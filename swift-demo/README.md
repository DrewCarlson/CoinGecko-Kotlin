Swift Demo
===

This module produces a framework with the core library and `ktor-client-ios`.
[coingecko-swift](coingecko-swift) is a complete Xcode project that consumes this framework.

The gradle module exists to package dependencies into a Framework for Swift, `src/commonMain/kotlin/empty.kt` exists to trigger compilation.

```kotlin
// build.gradle.kts
plugins { kotlin("multiplatform") }
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
```

The framework output defined above is created by running:
```bash
./gradlew :swift-demo:linkDebugFrameworkIosX64
```
Artifacts are stored in `swift-demo/build/bin/iosX64/debugFramework`.

Creating `CoinGeckoService` without parameters uses a default HttpClient with the available engine, no extra code is required.
```swift
let coinGecko = CoinGeckoService.init()
```