package drewcarlson.coingecko.domain.coins.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeveloperData(
        val forks: Long = 0,
        val stars: Long = 0,
        val subscribers: Long = 0,
        @SerialName("total_issues")
        val totalIssues: Long = 0,
        @SerialName("closed_issues")
        val closedIssues: Long = 0,
        @SerialName("pull_requests_merged")
        val pullRequestsMerged: Long = 0,
        @SerialName("pull_request_contributors")
        val pullRequestContributors: Long = 0,
        @SerialName("code_additions_deletions_4_weeks")
        val codeAdditionsDeletions4Weeks: CodeAdditionsDeletions4Weeks? = null,
        @SerialName("commit_count_4_weeks")
        val commitCount4Weeks: Long = 0,
        @SerialName("last_4_weeks_commit_activity_series")
        val last4WeeksCommitActivitySeries: List<Long>? = null
)