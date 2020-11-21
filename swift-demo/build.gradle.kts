plugins {
    kotlin("multiplatform")
}

kotlin {

    iosX64 {
        binaries {
            framework(listOf(DEBUG)) {
                baseName = "coingecko"
                export(rootProject)
            }
        }
    }

    sourceSets {
        val iosX64Main by getting {
            dependencies {
                api(rootProject)
                implementation("io.ktor:ktor-client-ios:$KTOR_VERSION")
            }
        }
    }
}