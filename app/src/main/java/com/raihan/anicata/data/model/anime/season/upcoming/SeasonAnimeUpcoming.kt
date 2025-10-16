package com.raihan.anicata.data.model.anime.season.upcoming

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonAnimeUpcoming(
    val id: Int,
    val url: String,
    val images: ImagesSeasonAnimeUpcoming,
    val trailer: TrailerSeasonAnimeUpcoming?,
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
    val aired: AiredSeasonAnimeUpcoming?,
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
    val broadcast: BroadcastSeasonAnimeUpcoming?,
    val producers: List<SeasonUpcomingDataProducers>,
    val licensors: List<SeasonUpcomingDataLicensors>,
    val studios: List<SeasonUpcomingDataStudios>,
    val genres: List<SeasonUpcomingDataGenres>,
    val explicitGenres: List<SeasonUpcomingDataExplicitGenres>,
    val themes: List<SeasonUpcomingDataThemes>,
    val demographics: List<SeasonUpcomingDataDemographics>
) : Parcelable

@Parcelize
data class ImagesSeasonAnimeUpcoming(
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
data class TrailerSeasonAnimeUpcoming(
    val youtubeId: String?,
    val url: String?,
    val embedUrl: String?
) : Parcelable

@Parcelize
data class AiredSeasonAnimeUpcoming(
    val from: String?,
    val to: String?,
    val prop: PropSeasonAnimeUpcoming?
) : Parcelable

@Parcelize
data class PropSeasonAnimeUpcoming(
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
data class BroadcastSeasonAnimeUpcoming(
    val day: String?,
    val time: String?,
    val timezone: String?,
    val string: String?
) : Parcelable

@Parcelize
data class SeasonUpcomingDataProducers(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonUpcomingDataLicensors(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonUpcomingDataStudios(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonUpcomingDataGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonUpcomingDataExplicitGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonUpcomingDataThemes(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SeasonUpcomingDataDemographics(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable
