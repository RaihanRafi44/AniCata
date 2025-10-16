package com.raihan.anicata.data.model.manga.top

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopManga(
    val id: Int,
    val url: String,
    val images: TopMangaImages,
    val approved: Boolean,
    val title: String,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val type: String?,
    val chapters: Int?,
    val volumes: Int?,
    val status: String?,
    val publishing: Boolean,
    val published: TopMangaPublished?,
    val score: Double?,
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val background: String?,
    val authors: List<TopMangaDataAuthors>,
    val serializations: List<TopMangaDataSerializations>,
    val genres: List<TopMangaDataGenres>,
    val explicitGenres: List<TopMangaDataExplicitGenres>,
    val themes: List<TopMangaDataThemes>,
    val demographics: List<TopMangaDataDemographics>
) : Parcelable

@Parcelize
data class TopMangaImages(
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
data class TopMangaPublished(
    val from: String?,
    val to: String?,
    val prop: TopMangaPublishedProp?
) : Parcelable

@Parcelize
data class TopMangaPublishedProp(
    val from: TopMangaDatePropFrom?,
    val to: TopMangaDatePropTo?,
    val string: String?
) : Parcelable

@Parcelize
data class TopMangaDatePropFrom(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class TopMangaDatePropTo(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class TopMangaDataAuthors(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopMangaDataSerializations(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopMangaDataGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopMangaDataExplicitGenres(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopMangaDataThemes(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TopMangaDataDemographics(
    val id: Int,
    val type: String?,
    val name: String?,
    val url: String?
) : Parcelable
