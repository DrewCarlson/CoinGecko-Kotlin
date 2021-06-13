[![](https://static.coingecko.com/s/coingecko-logo-d13d6bcceddbb003f146b33c2f7e8193d72b93bb343d38e392897c3df3e78bdd.png)](https://coingecko.com)

# CoinGecko Kotlin


![Maven Central](https://img.shields.io/maven-central/v/org.drewcarlson/coingecko-jvm?label=maven&color=blue)
![](https://github.com/DrewCarlson/CoinGecko-Kotlin/workflows/Jvm/badge.svg)
![](https://github.com/DrewCarlson/CoinGecko-Kotlin/workflows/Js/badge.svg)
![](https://github.com/DrewCarlson/CoinGecko-Kotlin/workflows/Native/badge.svg)

Kotlin wrapper for the [CoinGecko API](https://www.coingecko.com/en/api) using [Ktor](https://ktor.io).

## Usage

For a comprehensive list of available endpoints and to understand the returned data, see [coingecko.com/en/api](https://www.coingecko.com/en/api).

Kotlin
```kotlin
val coinGecko = CoinGeckoClient.create()

println(coinGecko.ping().geckoSays)
// Ping(geckoSays=(V3) To the Moon!)

println(coinGecko.getCoinById("ethereum"))
// CoinFullData(id=ethereum, symbol=eth, name=Ethereum, ...)
```
Swift
```swift
let coinGecko = CoinGeckoClientCompanion().create()
coinGecko.ping { (ping, error) in
    // ...
}
``` 

## Download


![Maven Central](https://img.shields.io/maven-central/v/org.drewcarlson/coingecko-jvm?label=maven&color=blue)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/org.drewcarlson/coingecko-jvm?server=https%3A%2F%2Fs01.oss.sonatype.org)

![](https://img.shields.io/static/v1?label=&message=Platforms&color=grey)
![](https://img.shields.io/static/v1?label=&message=Js&color=blue)
![](https://img.shields.io/static/v1?label=&message=Jvm&color=blue)
![](https://img.shields.io/static/v1?label=&message=Linux&color=blue)
![](https://img.shields.io/static/v1?label=&message=macOS&color=blue)
![](https://img.shields.io/static/v1?label=&message=Windows&color=blue)
![](https://img.shields.io/static/v1?label=&message=iOS&color=blue)
![](https://img.shields.io/static/v1?label=&message=tvOS&color=blue)
![](https://img.shields.io/static/v1?label=&message=watchOS&color=blue)

```kotlin
repositories {
  mavenCentral()
  // Or snapshots
  maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
  implementation("org.drewcarlson:coingecko:$coingecko_version")
}
```


Note: it is required to specify a Ktor client engine implementation.
([Documentation](https://ktor.io/clients/http-client/multiplatform.html))

```groovy
dependencies {
  // Jvm/Android
  implementation("io.ktor:ktor-client-okhttp:$ktor_version")
  implementation("io.ktor:ktor-client-android:$ktor_version")
  // iOS
  implementation("io.ktor:ktor-client-ios:$ktor_version")
  // macOS/Windows/Linux
  implementation("io.ktor:ktor-client-curl:$ktor_version")
  // Javascript/NodeJS
  implementation("io.ktor:ktor-client-js:$ktor_version")
}
``` 

## Swift Demo

The [swift-demo](swift-demo) module provides a Framework compilation module, and a complete [Xcode project](swift-demo/coingecko-swift) written in Swift.

## License
```
Copyright (c) 2020 Andrew Carlson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
