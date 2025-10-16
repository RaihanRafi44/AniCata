package com.raihan.anicata.data.source.network.model.anime.full


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AnimeDetailFullData(
    @SerializedName("aired")
    var aired: Aired?,
    @SerializedName("airing")
    var airing: Boolean?,
    @SerializedName("approved")
    var approved: Boolean?,
    @SerializedName("background")
    var background: String?,
    @SerializedName("broadcast")
    var broadcast: Broadcast?,
    @SerializedName("demographics")
    var demographics: List<Demographic>?,
    @SerializedName("duration")
    var duration: String?,
    @SerializedName("episodes")
    var episodes: Int?,
    @SerializedName("explicit_genres")
    var explicitGenres: List<ExplicitGenre>?,
    @SerializedName("external")
    var external: List<External>?,
    @SerializedName("favorites")
    var favorites: Int?,
    @SerializedName("genres")
    var genres: List<Genre>?,
    @SerializedName("images")
    var images: Images?,
    @SerializedName("licensors")
    var licensors: List<Licensor>?,
    @SerializedName("mal_id")
    var malId: Int?,
    @SerializedName("members")
    var members: Int?,
    @SerializedName("popularity")
    var popularity: Int?,
    @SerializedName("producers")
    var producers: List<Producer>?,
    @SerializedName("rank")
    var rank: Int?,
    @SerializedName("rating")
    var rating: String?,
    @SerializedName("relations")
    var relations: List<Relation>?,
    @SerializedName("score")
    var score: Double?,
    @SerializedName("scored_by")
    var scoredBy: Int?,
    @SerializedName("season")
    var season: String?,
    @SerializedName("source")
    var source: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("streaming")
    var streaming: List<Streaming>?,
    @SerializedName("studios")
    var studios: List<Studio>?,
    @SerializedName("synopsis")
    var synopsis: String?,
    @SerializedName("theme")
    var theme: Theme?,
    @SerializedName("themes")
    var themes: List<ThemeX>?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("title_english")
    var titleEnglish: String?,
    @SerializedName("title_japanese")
    var titleJapanese: String?,
    @SerializedName("title_synonyms")
    var titleSynonyms: List<String>?,
    @SerializedName("trailer")
    var trailer: Trailer?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("year")
    var year: Int?
)