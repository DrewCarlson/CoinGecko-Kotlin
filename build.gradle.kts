import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.binaryCompat)
    alias(libs.plugins.spotless)
}

allprojects {
    repositories {
        mavenCentral()
    }
}

apply(from = "gradle/publishing.gradle.kts")

yarn.lockFileDirectory = file("gradle/kotlin-js-store")

kotlin {
    jvm()
    js(IR) {
        browser {
            testTask {
                useMocha {
                    timeout = "15000"
                }
            }
        }
        nodejs {
            testTask {
                useMocha {
                    timeout = "15000"
                }
            }
        }
    }
    macosX64("macos")
    mingwX64("win64")
    linuxX64()

    iosArm64()
    iosX64()
    iosSimulatorArm64()
    watchosArm32()
    watchosArm64()
    watchosX86()
    tvos()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(libs.coroutines.core)
                implementation(libs.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.contentNegotiation)
                implementation(libs.ktor.serialization)
            }
        }
        val commonTest by getting {
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

        val win64Test by getting
        val macosTest by getting
        val linuxX64Test by getting
        configure(listOf(win64Test, macosTest, linuxX64Test)) {
            dependsOn(desktopCommonTest)
        }

        val iosMain by creating {
            dependsOn(commonMain)
        }
        val iosTest by creating {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.ktor.client.darwin)
            }
        }

        sourceSets.filter { sourceSet ->
            sourceSet.name.run {
                startsWith("iosX64") ||
                    startsWith("iosArm") ||
                    startsWith("iosSimulator")
            }
        }.forEach { sourceSet ->
            if (sourceSet.name.endsWith("Main")) {
                sourceSet.dependsOn(iosMain)
            } else {
                sourceSet.dependsOn(iosTest)
            }
        }

        // Configure tvos and watchos to build on ios sources
        val tvosTest by getting
        val watchosArm32Test by getting
        val watchosArm64Test by getting
        val watchosX86Test by getting
        configure(listOf(tvosTest, watchosArm32Test, watchosArm64Test, watchosX86Test)) {
            dependsOn(iosTest)
        }
    }
}

spotless {
    kotlin {
        target("**/**.kt")
        ktlint(libs.versions.ktlint.get())
            .setUseExperimental(true)
            .editorConfigOverride(mapOf(
                "disabled_rules" to "no-wildcard-imports,no-unused-imports,trailing-comma"
            ))
    }
}
