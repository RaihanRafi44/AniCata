package com.raihan.anicata.data.model.anime.top

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopAnime(
    val id: Int,
    val url: String,
    val images: ImagesTopAnime,
    val trailer: TrailerTopAnime?,
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
    val aired: AiredTopAnime?,
    val duration: String?,
    val rating: String?,
    val score: Double?,
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val background: String?,
    val season: String?,
    val year: Int?,
    val broadcast: BroadcastTopAnime?,
    val producers: List<TopDataProducers>,
    val licensors: List<TopDataLicensors>,
    val studios: List<TopDataStudios>,
    val genres: List<TopDataGenres>,
    val explicitGenres: List<TopDataExplicitGenres>,
    val themes: List<TopDataThemes>,
    val demographics: List<TopDataDemographics>
) : Parcelable

@Parcelize
data class ImagesTopAnime(
    val jpg: ImageFormatJpg,
    val webp: ImageFormatWebp
) : Parcelable

@Parcelize
data class ImageFormatJpg(
    val imageUrl: String?,
    val smallImageUrl: String?,
    val largeImageUrl: String?
) : Parcelable

@Parcelize
data class ImageFormatWebp(
    val imageUrl: String?,
    val smallImageUrl: String?,
    val largeImageUrl: String?
) : Parcelable

@Parcelize
data class TrailerTopAnime(
    val youtubeId: String?,
    val url: String?,
    val embedUrl: String?
) : Parcelable

@Parcelize
data class AiredTopAnime(
    val from: String?,
    val to: String?,
    val prop: PropTopAnime?
) : Parcelable

@Parcelize
data class PropTopAnime(
    val from: DatePropFrom?,
    val to: DatePropTo?,
    val string: String?
) : Parcelable

@Parcelize
data class DatePropFrom(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class DatePropTo(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class BroadcastTopAnime(
    val day: String?,
    val time: String?,
    val timezone: String?,
    val string: String?
) : Parcelable

@Parcelize
data class TopDataProducers(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopDataLicensors(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopDataStudios(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopDataGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopDataExplicitGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopDataThemes(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopDataDemographics(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable
