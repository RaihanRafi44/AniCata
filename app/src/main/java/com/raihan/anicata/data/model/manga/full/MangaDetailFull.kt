package com.raihan.anicata.data.model.manga.full

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MangaDetailFull(
    val id: Int,
    val url: String,
    val images: MangaFullImages,
    val approved: Boolean,
    val title: String,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val titleSynonyms: List<String>,
    val type: String,
    val chapters: Int?,
    val volumes: Int?,
    val status: String,
    val publishing: Boolean,
    val published: MangaFullPublished,
    val score: Double?,
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val background: String?,
    val authors: List<MangaFullEntityAuthors>,
    val serializations: List<MangaFullEntitySerialization>,
    val genres: List<MangaFullEntityGenres>,
    val explicitGenres: List<MangaFullEntityExplicitGenres>,
    val themes: List<MangaFullEntityThemes>,
    val demographics: List<MangaFullEntityDemographics>,
    val relations: List<MangaFullRelation>,
    val external: List<MangaFullExternal>
) : Parcelable

@Parcelize
data class MangaFullImages(
    val jpg: MangaImageTypeJpg,
    val webp: MangaImageTypeWebp
) : Parcelable

@Parcelize
data class MangaImageTypeJpg(
    val imageUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String
) : Parcelable

@Parcelize
data class MangaImageTypeWebp(
    val imageUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String
) : Parcelable

@Parcelize
data class MangaFullPublished(
    val from: String?,
    val to: String?,
    val prop: MangaFullPublishedProp
) : Parcelable

@Parcelize
data class MangaFullPublishedProp(
    val from: MangaFullDateFrom?,
    val to: MangaFullDateTo?,
    val string: String?
) : Parcelable

@Parcelize
data class MangaFullDateFrom(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class MangaFullDateTo(
    val day: Int?,
    val month: Int?,
    val year: Int?
) : Parcelable

@Parcelize
data class MangaFullRelation(
    val relation: String,
    val entry: List<MangaFullEntityRelation>
) : Parcelable

@Parcelize
data class MangaFullExternal(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MangaFullEntityAuthors(
    val id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MangaFullEntitySerialization(
    val id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MangaFullEntityGenres(
    val id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MangaFullEntityExplicitGenres(
    val id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MangaFullEntityThemes(
    val id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MangaFullEntityDemographics(
    val id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class MangaFullEntityRelation(
    val id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable
