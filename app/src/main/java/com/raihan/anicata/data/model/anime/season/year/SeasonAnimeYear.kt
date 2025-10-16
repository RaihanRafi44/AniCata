package com.raihan.anicata.data.model.anime.season.year

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonAnimeYear(
    val id: Int,
    val url: String,
    val images: ImagesSeasonAnimeYear,
    val trailer: TrailerSeasonAnimeYear?,
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
    val aired: AiredSeasonAnimeYear?,
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
    val broadcast: BroadcastSeasonAnimeYear?,
    val producers: List<SeasonYearDataProducers>,
    val licensors: List<SeasonYearDataLicensors>,
    val studios: List<SeasonYearDataStudios>,
    val genres: List<SeasonYearDataGenres>,
    val explicitGenres: List<SeasonYearDataExplicitGenres>,
    val themes: List<SeasonYearDataThemes>,
    val demographics: List<SeasonYearDataDemographics>
) : Parcelable

@Parcelize
data class ImagesSeasonAnimeYear(
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
data class TrailerSeasonAnimeYear(
    val youtubeId: String?,
    val url: String?,
    val embedUrl: String?
) : Parcelable

@Parcelize
data class AiredSeasonAnimeYear(
    val from: String?,
    val to: String?,
    val prop: PropSeasonAnimeYear?
) : Parcelable

@Parcelize
data class PropSeasonAnimeYear(
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
data class BroadcastSeasonAnimeYear(
    val day: String?,
    val time: String?,
    val timezone: String?,
    val string: String?
) : Parcelable

@Parcelize
data class SeasonYearDataProducers(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonYearDataLicensors(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonYearDataStudios(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonYearDataGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonYearDataExplicitGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonYearDataThemes(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonYearDataDemographics(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable
