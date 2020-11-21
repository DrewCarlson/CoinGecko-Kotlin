package drewcarlson.coingecko.paging

interface Page {
    val total: Int
    val perPage: Int
    val nextPage: Int?
    val previousPage: Int?
}
