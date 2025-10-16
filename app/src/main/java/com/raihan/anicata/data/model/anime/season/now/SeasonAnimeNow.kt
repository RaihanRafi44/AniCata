package com.raihan.anicata.data.model.anime.season.now

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonAnimeNow(
    val id: Int,
    val url: String,
    val images: ImagesSeasonAnimeNow,
    val trailer: TrailerSeasonAnimeNow?,
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
    val aired: AiredSeasonAnimeNow?,
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
    val broadcast: BroadcastSeasonAnimeNow?,
    val producers: List<SeasonNowDataProducers>,
    val licensors: List<SeasonNowDataLicensors>,
    val studios: List<SeasonNowDataStudios>,
    val genres: List<SeasonNowDataGenres>,
    val explicitGenres: List<SeasonNowDataExplicitGenres>,
    val themes: List<SeasonNowDataThemes>,
    val demographics: List<SeasonNowDataDemographics>
) : Parcelable

@Parcelize
data class ImagesSeasonAnimeNow(
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
data class TrailerSeasonAnimeNow(
    val youtubeId: String?,
    val url: String?,
    val embedUrl: String?
) : Parcelable

@Parcelize
data class AiredSeasonAnimeNow(
    val from: String?,
    val to: String?,
    val prop: PropSeasonAnimeNow?
) : Parcelable

@Parcelize
data class PropSeasonAnimeNow(
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
data class BroadcastSeasonAnimeNow(
    val day: String?,
    val time: String?,
    val timezone: String?,
    val string: String?
) : Parcelable

@Parcelize
data class SeasonNowDataProducers(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonNowDataLicensors(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonNowDataStudios(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonNowDataGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonNowDataExplicitGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonNowDataThemes(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonNowDataDemographics(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

