import Foundation
import coingecko

final class PriceObservable: ObservableObject {
    let coinId: String
    let target: String
    @Published var coinPrice: CoinPrice?
    
    private let coinGecko: CoinGeckoClient

    init(coinId: String, target: String) {
        self.coinId = coinId
        self.target = target
        coinGecko = CoinGeckoService.init()
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

