package com.raihan.anicata.data.source.network.model.manga.full


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MangaDetailFullData(
    @SerializedName("approved")
    var approved: Boolean?,
    @SerializedName("authors")
    var authors: List<Author>?,
    @SerializedName("background")
    var background: String?,
    @SerializedName("chapters")
    var chapters: Int?,
    @SerializedName("demographics")
    var demographics: List<Demographic>?,
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
    @SerializedName("mal_id")
    var malId: Int?,
    @SerializedName("members")
    var members: Int?,
    @SerializedName("popularity")
    var popularity: Int?,
    @SerializedName("published")
    var published: Published?,
    @SerializedName("publishing")
    var publishing: Boolean?,
    @SerializedName("rank")
    var rank: Int?,
    @SerializedName("relations")
    var relations: List<Relation>?,
    @SerializedName("score")
    var score: Double?,
    @SerializedName("scored_by")
    var scoredBy: Int?,
    @SerializedName("serializations")
    var serializations: List<Serialization>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("synopsis")
    var synopsis: String?,
    @SerializedName("themes")
    var themes: List<Theme>?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("title_english")
    var titleEnglish: String?,
    @SerializedName("title_japanese")
    var titleJapanese: String?,
    @SerializedName("title_synonyms")
    var titleSynonyms: List<String>?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("volumes")
    var volumes: Int?
)