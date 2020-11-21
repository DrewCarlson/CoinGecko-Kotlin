plugins {
    kotlin("multiplatform") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
}

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/kotlinx") }
}

apply(plugin = "maven-publish")

configure<PublishingExtension> {
    repositories {
        maven {
            credentials {
                username = System.getenv("BINTRAY_USER")
                password = System.getenv("BINTRAY_API_KEY")
            }
            url = uri("https://kotlin.bintray.com/kotlinx")
        }
    }
}

val ktor_version: String by ext
val coroutines_version: String by ext

kotlin {
    jvm()
    js(BOTH) {
        browser {
            testTask {
                useMocha {
                    timeout = "10000"
                }
            }
        }
        nodejs {
            testTask {
                useMocha {
                    timeout = "10000"
                }
            }
        }
    }
    macosX64("macos")
    mingwX64("win64")
    linuxX64()

    ios {
        binaries {
            framework(listOf(DEBUG)) {

            }
        }
    }
    watchos()
    tvos()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-json:$ktor_version")
                implementation("io.ktor:ktor-client-serialization:$ktor_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("io.ktor:ktor-client-core-jvm:$ktor_version")
                implementation("io.ktor:ktor-client-json-jvm:$ktor_version")
                implementation("io.ktor:ktor-client-serialization-jvm:$ktor_version")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("io.ktor:ktor-client-okhttp:$ktor_version")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("io.ktor:ktor-client-core-js:$ktor_version")
                implementation("io.ktor:ktor-client-json-js:$ktor_version")
                implementation("io.ktor:ktor-client-serialization-js:$ktor_version")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-js"))
                implementation("io.ktor:ktor-client-js:$ktor_version")
                implementation(npm("node-fetch", "*"))
                implementation(npm("text-encoding", "*"))
                implementation(npm("bufferutil", "*"))
                implementation(npm("utf-8-validate", "*"))
                implementation(npm("abort-controller", "*"))
                implementation(npm("fs", "*"))
            }
        }

        val nativeCommonMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-json:$ktor_version")
                implementation("io.ktor:ktor-client-serialization:$ktor_version")
            }
        }
        val nativeCommonTest by creating {
            dependsOn(commonTest)
        }
        val desktopCommonMain by creating {
            dependsOn(nativeCommonMain)
        }
        val desktopCommonTest by creating {
            dependsOn(nativeCommonTest)
            dependencies {
                implementation("io.ktor:ktor-client-curl:$ktor_version")
            }
        }

        val win64Main by getting
        val macosMain by getting
        val linuxX64Main by getting
        configure(listOf(win64Main, macosMain, linuxX64Main)) {
            dependsOn(desktopCommonMain)
        }

        val win64Test by getting
        val macosTest by getting
        val linuxX64Test by getting
        configure(listOf(win64Test, macosTest, linuxX64Test)) {
            dependsOn(desktopCommonTest)
        }

        val iosMain by getting {
            dependsOn(nativeCommonMain)
        }
        val iosTest by getting {
            dependsOn(nativeCommonTest)
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktor_version")
            }
        }

        // Configure tvos and watchos to build on ios sources
        val tvosMain by getting
        val tvosTest by getting
        val watchosMain by getting
        val watchosTest by getting
        configure(listOf(tvosMain, watchosMain)) {
            dependsOn(iosMain)
        }
        configure(listOf(tvosTest, watchosTest)) {
            dependsOn(iosTest)
            kotlin.srcDirs("src/iosTest/kotlin")
        }

        // Added for use in the swift demo
        val iosX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktor_version")
            }
        }
    }
}