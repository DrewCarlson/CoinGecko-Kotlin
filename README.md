[![](https://static.coingecko.com/s/coingecko-logo-d13d6bcceddbb003f146b33c2f7e8193d72b93bb343d38e392897c3df3e78bdd.png)](https://coingecko.com)

# CoinGecko Kotlin

![Bintray](https://img.shields.io/bintray/v/drewcarlson/CoinGecko-Kotlin/CoinGecko-Kotlin?color=blue)
![](https://github.com/DrewCarlson/CoinGecko-Kotlin/workflows/Jvm%20Tests/badge.svg)
![](https://github.com/DrewCarlson/CoinGecko-Kotlin/workflows/Js%20Tests/badge.svg)
![](https://github.com/DrewCarlson/CoinGecko-Kotlin/workflows/Native%20Tests/badge.svg)

Kotlin wrapper for the [CoinGecko API](https://www.coingecko.com/en/api).

## About

CoinGecko-Kotlin is written in common Kotlin to support multiplatform development.  [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) provides json (de)serialization and [Ktor](https://ktor.io) the HTTP API.

## Usage

For a comprehensive list of available endpoints and to understand the returned data, see [coingecko.com/en/api](https://www.coingecko.com/en/api).

```kotlin
val httpClient = HttpClient(OkHttp) // ktor + okhttp (jvm)
val coinGecko = CoinGeckoService(httpClient)

println(coinGecko.getCoinById("ethereum"))
// CoinFullData(id=ethereum, symbol=eth, name=Ethereum, ...)
```

## Download

![](https://img.shields.io/static/v1?label=&message=Platforms&color=grey)
![](https://img.shields.io/static/v1?label=&message=Js&color=blue)
![](https://img.shields.io/static/v1?label=&message=Jvm&color=blue)
![](https://img.shields.io/static/v1?label=&message=Linux&color=blue)
![](https://img.shields.io/static/v1?label=&message=macOS&color=blue)
![](https://img.shields.io/static/v1?label=&message=Windows&color=blue)
![](https://img.shields.io/static/v1?label=&message=iOS&color=blue)
![](https://img.shields.io/static/v1?label=&message=tvOS&color=blue)
![](https://img.shields.io/static/v1?label=&message=watchOS&color=blue)

Artifacts are available on [Bintray](https://bintray.com/drewcarlson/CoinGecko-Kotlin).

```groovy
repositories {
  jcenter()
}

dependencies {
  implementation "drewcarlson.coingecko:coingecko-jvm:$coingecko_version"
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

When compiling for Apple targets, Kotlin 1.4+ will output methods with a `completionHandler` argument when marked with `suspend`.
This allows APIs like `CoinGeckoClient` to be used seamlessly in Swift.  [coingecko-swift](coingecko-swift) provides a complete Xcode demo project.
Note that `SwiftShims.createHttpClient()` is provided in the `iosSim` target for demo purposes, the arm 32/64 targets do not include this shim or the Ktor iOS client.

Swift Example:
```swift
let httpClient = SwiftShims().createHttpClient()
let coinGecko = CoinGeckoService.init(httpClient: httpClient)
coinGecko.getPrice(
    ids: "bitcoin",
    vsCurrencies: "usd",
    includeMarketCap: false,
    include24hrVol: false,
    include24hrChange: false,
    includeLastUpdatedAt: false
) { (priceMap, error) in
    priceMap!["bitcoin"].getPrice("usd")
}
``` 


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