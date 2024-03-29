import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import java.time.Duration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.binaryCompat)
    alias(libs.plugins.spotless)
    alias(libs.plugins.mavenPublish)
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.atomicfu.plugin)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

apply(plugin = "kotlinx-atomicfu")

plugins.withType<NodeJsRootPlugin> {
    the<YarnRootExtension>().lockFileDirectory = rootDir.resolve("gradle/kotlin-js-store")
}

version = System.getenv("GITHUB_REF")?.substringAfter("refs/tags/v", version.toString()) ?: version

val testGenSrcPath = "build/test-gen/config"
val installTestConfig by tasks.creating {
    val configFile = file("${testGenSrcPath}/config.kt")
    onlyIf { !configFile.exists() || gradle.startParameter.taskNames.contains("publish") }
    doFirst {
        file(testGenSrcPath).mkdirs()
        if (!configFile.exists()) {
            val demoKey = System.getProperty("demoApiKey")?.toString().orEmpty()
            configFile.writeText(
                """|package coingecko
                   |
                   |val DEMO_API_KEY = "$demoKey"
                   |""".trimMargin(),
            )
        }
    }
}

kotlin {
    jvm()
    js(IR) {
        browser {
            testTask {
                useKarma {
                    useFirefoxHeadless()
                    timeout.set(Duration.ofSeconds(30))
                }
            }
        }
        nodejs {
            testTask {
                useMocha {
                    timeout = "30000"
                }
            }
        }
    }
    macosX64()
    macosArm64()
    mingwX64("win64")
    linuxX64()

    iosArm64()
    iosX64()
    iosSimulatorArm64()
    watchosArm32()
    watchosArm64()
    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()
    applyDefaultHierarchyTemplate()

    targets.all {
        compilations
            .filter { it.name.endsWith("test", ignoreCase = true) }
            .forEach {
                it.compileTaskProvider.configure {
                    dependsOn(installTestConfig)
                }
            }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(libs.coroutines.core)
                implementation(libs.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.contentNegotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.atomicfu)
            }
        }
        val commonTest by getting {
            kotlin.srcDirs(testGenSrcPath)
            dependencies {
                implementation(libs.coroutines.test)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation(libs.ktor.client.okhttp)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-js"))
                implementation(libs.ktor.client.js)
            }
        }

        val nativeCommonTest by creating {
            dependsOn(commonTest)
        }
        val desktopCommonTest by creating {
            dependsOn(nativeCommonTest)
            dependencies {
                implementation(libs.ktor.client.curl)
            }
        }

        val win64Test by getting {
            dependencies {
                implementation(libs.ktor.client.winhttp)
            }
        }
        val macosTest by getting
        val linuxX64Test by getting
        configure(listOf(macosTest, linuxX64Test)) {
            dependsOn(desktopCommonTest)
        }

        val iosTest by getting {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.ktor.client.darwin)
            }
        }

        // Configure tvos and watchos to build on ios sources
        val tvosTest by getting
        val watchosArm32Test by getting
        val watchosArm64Test by getting
        configure(listOf(tvosTest, watchosArm32Test, watchosArm64Test)) {
            dependsOn(iosTest)
        }
    }
}

spotless {
    kotlin {
        target("**/**.kt")
        ktlint(libs.versions.ktlint.get())
            .setUseExperimental(true)
            .editorConfigOverride(
                mapOf(
                    "ktlint_standard_no-wildcard-imports" to "disabled",
                    "ktlint_standard_no-unused-imports" to "disabled",
                    "ij_kotlin_allow_trailing_comma" to "true",
                    "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
                )
            )
    }
}
