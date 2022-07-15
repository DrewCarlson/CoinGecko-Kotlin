import java.net.URL
import java.net.HttpURLConnection
import java.util.Base64

apply(plugin = "maven-publish")
apply(plugin = "signing")
apply(plugin = "org.jetbrains.dokka")

System.getenv("GITHUB_REF")?.let { ref ->
    if (ref.startsWith("refs/tags/v")) {
        version = ref.substringAfterLast("refs/tags/v")
    }
}

val isSnapshot by lazy { version.toString().endsWith("SNAPSHOT") }

val mavenUrl: String by extra
val mavenSnapshotUrl: String by extra
val signingKey: String? by project
val signingPassword: String? by project
val sonatypeUsername: String? by project
val sonatypePassword: String? by project
val sonatypeStagingProfile: String? by project
val pomProjectUrl: String by project
val pomProjectDescription: String by project
val pomScmUrl: String by project
val pomDeveloperId: String by project
val pomDeveloperName: String by project
val pomLicenseName: String by project
val pomLicenseUrl: String by project
val pomLicenseDistribution: String by project

task<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
}

val repositoryId by lazy {
    val hasPublishingTask = gradle.startParameter.taskNames.any {
        it == "publish" || (it.startsWith("publish") && it.contains("MavenRepository"))
    }
    if (!hasPublishingTask || isSnapshot) return@lazy ""
    if (!rootProject.extra.has("publishRepositoryId")) {
        val id = makeRequest("start", "{ description: \"${rootProject.name} v${version}\" }")
            .substringAfter("stagedRepositoryId\":\"").substringBefore('"')
        rootProject.extra.set("publishRepositoryId", id)
    }
    rootProject.extra.get("publishRepositoryId")
}

task("closeRepository") {
    doLast { makeRequest("finish", "{ stagedRepositoryId: \"${repositoryId}\" }") }
}

configure<PublishingExtension> {
    components.findByName("java")?.also { javaComponent ->
        task<Jar>("sourcesJar") {
            archiveClassifier.set("sources")
            val sourceSets = project.extensions.getByName<SourceSetContainer>("sourceSets")
            from(sourceSets["main"].allSource)
        }
        publications.create<MavenPublication>("mavenJava") {
            from(javaComponent)
            artifact(tasks["sourcesJar"])
        }
    }
    publications.withType<MavenPublication> {
        artifact(tasks.named("javadocJar"))
        with(pom) {
            name.set(rootProject.name)
            url.set(pomProjectUrl)
            description.set(pomProjectDescription)
            scm {
                url.set(pomScmUrl)
            }
            developers {
                developer {
                    id.set(pomDeveloperId)
                    name.set(pomDeveloperName)
                }
            }
            licenses {
                license {
                    name.set(pomLicenseName)
                    url.set(pomLicenseUrl)
                    distribution.set(pomLicenseDistribution)
                }
            }
        }
    }
    repositories {
        maven {
            url = if (isSnapshot) {
                uri(mavenSnapshotUrl)
            } else {
                uri("${mavenUrl}deployByRepositoryId/$repositoryId")
            }
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
}

if (!isSnapshot) {
    tasks.named("publish") { finalizedBy("closeRepository") }
}

configure<SigningExtension> {
    isRequired = !isSnapshot
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign((extensions["publishing"] as PublishingExtension).publications)
}

fun makeRequest(path: String, data: String? = null): String {
    val auth = Base64.getEncoder().encode("$sonatypeUsername:$sonatypePassword".toByteArray())
    return (URL("${mavenUrl}profiles/$sonatypeStagingProfile/${path}").openConnection() as HttpURLConnection).run {
        setRequestProperty("Authorization", "Basic ${auth.decodeToString()}")
        if (data != null) {
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            getOutputStream().write("{ data: $data }".toByteArray())
        }
        connect()
        val stream = runCatching { getInputStream() }.getOrNull() ?: getErrorStream()
        checkNotNull(stream?.readBytes()?.decodeToString()) { "Failed to extract a response body." }
    }
}
