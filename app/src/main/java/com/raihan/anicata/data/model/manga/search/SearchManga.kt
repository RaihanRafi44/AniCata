package com.raihan.anicata.data.model.manga.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchManga(
    val id: Int,
    val url: String,
    val images: MangaImages,
    val approved: Boolean,
    val title: String,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val type: String?,
    val chapters: Int?,
    val volumes: Int?,
    val status: String?,
    val publishing: Boolean,
    val published: PublishedSearchManga?,
    val score: Double?,
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val background: String?,
    val authors: List<SearchMangaDataAuthors>,
    val serializations: List<SearchMangaDataSerializations>,
    val genres: List<SearchMangaDataGenres>,
    val explicitGenres: List<SearchMangaDataExplicitGenres>,
    val themes: List<SearchMangaDataThemes>,
    val demographics: List<SearchMangaDataDemographics>
) : Parcelable

@Parcelize
data class MangaImages(
    val jpg: MangaImageTypeJpg,
    val webp: MangaImageTypeWebp
) : Parcelable

@Parcelize
data class MangaImageTypeJpg(
    val imageUrl: String?,
    val smallImageUrl: String?,
    val largeImageUrl: String?
) : Parcelable

@Parcelize
data class MangaImageTypeWebp(
    val imageUrl: String?,
    val smallImageUrl: String?,
    val largeImageUrl: String?
) : Parcelable

@Parcelize
data class PublishedSearchManga(
    val from: String?,
    val to: String?,
    val prop: PublishedPropSearchManga?
) : Parcelable

@Parcelize
data class PublishedPropSearchManga(
    val from: DatePropFromSearchManga?,
    val to: DatePropToSearchManga?,
    val string: String?
) : Parcelable

@Parcelize
data class DatePropFromSearchManga(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class DatePropToSearchManga(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class SearchMangaDataAuthors(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchMangaDataSerializations(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchMangaDataGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchMangaDataExplicitGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchMangaDataThemes(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class SearchMangaDataDemographics(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable
