package drewcarlson.coingecko.models.coins.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CommunityData(
        @SerialName("facebook_likes")
        val facebookLikes: Double = 0.0,
        @SerialName("twitter_followers")
        val twitterFollowers: Double = 0.0,
        @SerialName("reddit_average_posts_48h")
        val redditAveragePosts48h: Double = 0.0,
        @SerialName("reddit_average_comments_48h")
        val redditAverageComments48h: Double = 0.0,
        @SerialName("reddit_subscribers")
        val redditSubscribers: Double = 0.0,
        @SerialName("reddit_accounts_active_48h")
        val redditAccountsActive48h: Double = 0.0,
        @SerialName("telegram_channel_user_count")
        val telegramChannelUserCount: Double?
)