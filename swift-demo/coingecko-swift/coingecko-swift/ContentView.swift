import SwiftUI
import coingecko

struct CoinPriceView: View {
    @ObservedObject var dataSource: PriceObservable
    private let formatter = NumberFormatter()
    private let coinGecko = CoinGeckoClientCompanion().create()

    init(coinId: String, target: String) {
        formatter.numberStyle = .currency
        formatter.currencyCode = target
        dataSource = PriceObservable(
            coinId: coinId,
            target: target,
            coinGecko: coinGecko
        )
    }

    var body: some View {
        let coinPrice = dataSource.coinPrice
        let priceView: Text
        if (coinPrice == nil) {
            priceView = Text("Loading...")
        } else {
            let price = coinPrice!.getPrice(currency: dataSource.target)
            priceView = Text(formatter.string(for: price)!)
        }
        return AnyView(VStack {
            Text(dataSource.coinId.capitalized).bold()
            priceView
        }.padding(5))
    }
}

struct ContentView: View {

    var body: some View {
        return AnyView(VStack {
            CoinPriceView(coinId: "bitcoin", target: "cad")
            CoinPriceView(coinId: "ethereum", target: "cad")
            CoinPriceView(coinId: "bread", target: "cad")
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
