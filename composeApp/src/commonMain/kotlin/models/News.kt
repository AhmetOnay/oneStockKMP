package models

import kotlinx.serialization.Serializable

@Serializable
data class News(
    val meta: Meta? = null,
    val data: List<NewsArticle>,
)
@Serializable
data class Meta(
    val found: Int? = null,
    val returned: Int? = null,
    val limit: Int? = null,
    val page: Int? = null
)
@Serializable
data class NewsArticle(
    val uuid: String? = null,
    val title: String,
    val description: String,
    val keywords: String? = null,
    val snippet: String? = null,
    val url: String,
    val image_url: String? = null,
    val language: String? = null,
    val published_at: String? = null,
    val source: String? = null,
    val relevance_score: Double? = null,
    val entities: List<Entity>? = null,
    val similar: List<NewsArticle>? = null
)
@Serializable
data class Entity(
    val symbol: String? = null,
    val name: String? = null,
    val exchange: String? = null,
    val exchange_long: String? = null,
    val country: String? = null,
    val type: String? = null,
    val industry: String? = null,
    val match_score: Double? = null,
    val sentiment_score: Double? = null,
    val highlights: List<Highlight>? = null
)
@Serializable
data class Highlight(
    val highlight: String? = null,
    val sentiment: Double? = null,
    val highlighted_in: String? = null
)