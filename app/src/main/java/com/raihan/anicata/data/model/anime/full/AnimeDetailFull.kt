package com.raihan.anicata.data.model.anime.full

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeData(
    val id: Int,
    val url: String,
    val images: ImagesDetail,
    val trailer: TrailerAnime?,
    val approved: Boolean,
    val title: String,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val titleSynonyms: List<String>,
    val type: String?,
    val source: String?,
    val episodes: Int?,
    val status: String?,
    val airing: Boolean,
    val aired: AiredAnime?,
    val duration: String?,
    val rating: String?,
    val score: Double?,
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int,
    val members: Int,
    val favorites: Int,
    val synopsis: String?,
    val background: String?,
    val season: String?,
    val year: Int?,
    val producers: List<MalUrlAnimeProducers>,
    val licensors: List<MalUrlAnimeLicensors>,
    val studios: List<MalUrlAnimeStudios>,
    val genres: List<MalUrlAnimeGenres>,
    val explicitGenres: List<MalUrlAnimeExplicitGenres>,
    val themes: List<MalUrlAnimeThemes>,
    val demographics: List<MalUrlAnimeDemographics>,
    val relations: List<RelationAnime>,
) : Parcelable

@Parcelize
data class ImagesDetail(
    val jpg: ImageSetJpg,
    val webp: ImageSetWebp
) : Parcelable

@Parcelize
data class ImageSetJpg(
    val imageUrl: String?,
    val smallImageUrl: String?,
    val largeImageUrl: String?
) : Parcelable

@Parcelize
data class ImageSetWebp(
    val imageUrl: String?,
    val smallImageUrl: String?,
    val largeImageUrl: String?
) : Parcelable

@Parcelize
data class TrailerAnime(
    val youtubeId: String?,
    val url: String?,
    val embedUrl: String?
) : Parcelable

@Parcelize
data class AiredAnime(
    val from: String?,
    val to: String?,
    val prop: PropAnime
) : Parcelable

@Parcelize
data class PropAnime(
    val from: DateInfoAnimeFrom,
    val to: DateInfoAnimeTo,
    val string: String
) : Parcelable

@Parcelize
data class DateInfoAnimeFrom(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class DateInfoAnimeTo(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class RelationAnime(
    val relation: String,
    val entry: List<MalUrlAnimeEntryRelation>
) : Parcelable

@Parcelize
data class MalUrlAnimeProducers(
    val malId: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MalUrlAnimeLicensors(
    val malId: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MalUrlAnimeStudios(
    val malId: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MalUrlAnimeGenres(
    val malId: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MalUrlAnimeExplicitGenres(
    val malId: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MalUrlAnimeThemes(
    val malId: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MalUrlAnimeDemographics(
    val malId: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MalUrlAnimeEntryRelation(
    val malId: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable