package com.raihan.anicata.data.model.anime.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchAnime(
    val id: Int,
    val url: String,
    val images: ImagesSearchAnime,
    val trailer: TrailerSearchAnime?,
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
    val aired: AiredSearchAnime?,
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
    val broadcast: BroadcastSearchAnime?,
    val producers: List<SearchDataProducers>,
    val licensors: List<SearchDataLicensors>,
    val studios: List<SearchDataStudios>,
    val genres: List<SearchDataGenres>,
    val explicitGenres: List<SearchDataExplicitGenres>,
    val themes: List<SearchDataThemes>,
    val demographics: List<SearchDataDemographics>
) : Parcelable

@Parcelize
data class ImagesSearchAnime(
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
data class TrailerSearchAnime(
    val youtubeId: String?,
    val url: String?,
    val embedUrl: String?
) : Parcelable

@Parcelize
data class AiredSearchAnime(
    val from: String?,
    val to: String?,
    val prop: PropSearchAnime?
) : Parcelable

@Parcelize
data class PropSearchAnime(
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
data class BroadcastSearchAnime(
    val day: String?,
    val time: String?,
    val timezone: String?,
    val string: String?
) : Parcelable

@Parcelize
data class SearchDataProducers(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchDataLicensors(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchDataStudios(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchDataGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchDataExplicitGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchDataThemes(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchDataDemographics(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable
