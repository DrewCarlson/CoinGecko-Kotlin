import Foundation
import coingecko

final class PriceObservable: ObservableObject {
    let coinId: String
    let target: String
    private let coinGecko: CoinGeckoClient
    @Published var coinPrice: CoinPrice?

    init(coinId: String, target: String, coinGecko: CoinGeckoClient) {
        self.coinId = coinId
        self.target = target
        self.coinGecko = coinGecko
        reload()
    }

    func reload() {
        coinGecko.getPrice(
            ids: coinId,
            vsCurrencies: target,
            includeMarketCap: false,
            include24hrVol: false,
            include24hrChange: false,
            includeLastUpdatedAt: false
        ) { (priceMap, error) in
            if (error != nil || priceMap == nil) {
                // TODO: Handle error
            } else {
                self.coinPrice = priceMap?[self.coinId]
            }
        }
    }
}

