package com.raihan.anicata.data.mapper.manga

import com.raihan.anicata.data.model.manga.top.MangaImageTypeJpg
import com.raihan.anicata.data.model.manga.top.MangaImageTypeWebp
import com.raihan.anicata.data.model.manga.top.TopManga
import com.raihan.anicata.data.model.manga.top.TopMangaDataAuthors
import com.raihan.anicata.data.model.manga.top.TopMangaDataDemographics
import com.raihan.anicata.data.model.manga.top.TopMangaDataExplicitGenres
import com.raihan.anicata.data.model.manga.top.TopMangaDataGenres
import com.raihan.anicata.data.model.manga.top.TopMangaDataSerializations
import com.raihan.anicata.data.model.manga.top.TopMangaDataThemes
import com.raihan.anicata.data.model.manga.top.TopMangaDatePropFrom
import com.raihan.anicata.data.model.manga.top.TopMangaDatePropTo
import com.raihan.anicata.data.model.manga.top.TopMangaImages
import com.raihan.anicata.data.model.manga.top.TopMangaPublished
import com.raihan.anicata.data.model.manga.top.TopMangaPublishedProp
import com.raihan.anicata.data.source.network.model.manga.top.manga.Author
import com.raihan.anicata.data.source.network.model.manga.top.manga.Demographic
import com.raihan.anicata.data.source.network.model.manga.top.manga.ExplicitGenre
import com.raihan.anicata.data.source.network.model.manga.top.manga.From
import com.raihan.anicata.data.source.network.model.manga.top.manga.Genre
import com.raihan.anicata.data.source.network.model.manga.top.manga.Images
import com.raihan.anicata.data.source.network.model.manga.top.manga.Jpg
import com.raihan.anicata.data.source.network.model.manga.top.manga.Prop
import com.raihan.anicata.data.source.network.model.manga.top.manga.Published
import com.raihan.anicata.data.source.network.model.manga.top.manga.Serialization
import com.raihan.anicata.data.source.network.model.manga.top.manga.Theme
import com.raihan.anicata.data.source.network.model.manga.top.manga.To
import com.raihan.anicata.data.source.network.model.manga.top.manga.TopMangaData
import com.raihan.anicata.data.source.network.model.manga.top.manga.Webp

fun TopMangaData?.toTopManga() =
    TopManga(
        id = this?.malId ?: 0,
        url = this?.url.orEmpty(),
        images = this?.images.toTopMangaImages(),
        approved = this?.approved ?: false,
        title = this?.title.orEmpty(),
        titleEnglish = this?.titleEnglish,
        titleJapanese = this?.titleJapanese,
        type = this?.type,
        chapters = this?.chapters,
        volumes = this?.volumes,
        status = this?.status,
        publishing = this?.publishing ?: false,
        published = this?.published.toTopMangaPublished(),
        score = this?.score,
        scoredBy = this?.scoredBy,
        rank = this?.rank,
        popularity = this?.popularity,
        members = this?.members,
        favorites = this?.favorites,
        synopsis = this?.synopsis,
        background = this?.background,
        authors = this?.authors.toTopMangaDataAuthors(),
        serializations = this?.serializations.toTopMangaDataSerializations(),
        genres = this?.genres.toTopMangaDataGenres(),
        explicitGenres = this?.explicitGenres.toTopMangaDataExplicitGenres(),
        themes = this?.themes.toTopMangaDataThemes(),
        demographics = this?.demographics.toTopMangaDataDemographics()
    )

// Images
fun Images?.toTopMangaImages() =
    TopMangaImages(
        jpg = this?.jpg.toTopMangaImageSetJpg(),
        webp = this?.webp.toTopMangaImageSetWebp()
    )

fun Jpg?.toTopMangaImageSetJpg() =
    MangaImageTypeJpg(
        imageUrl = this?.imageUrl,
        smallImageUrl = this?.smallImageUrl,
        largeImageUrl = this?.largeImageUrl
    )

fun Webp?.toTopMangaImageSetWebp() =
    MangaImageTypeWebp(
        imageUrl = this?.imageUrl,
        smallImageUrl = this?.smallImageUrl,
        largeImageUrl = this?.largeImageUrl
    )

// Published
fun Published?.toTopMangaPublished() =
    TopMangaPublished(
        from = this?.from,
        to = this?.to,
        prop = this?.prop.toTopMangaPublishedProp()
    )

fun Prop?.toTopMangaPublishedProp() =
    TopMangaPublishedProp(
        from = this?.from.toTopMangaDateInfoFrom(),
        to = this?.to.toTopMangaDateInfoTo(),
        string = this?.string
    )

fun From?.toTopMangaDateInfoFrom() =
    TopMangaDatePropFrom(
        day = this?.day,
        month = this?.month,
        year = this?.year
    )

fun To?.toTopMangaDateInfoTo() =
    TopMangaDatePropTo(
        day = this?.day,
        month = this?.month,
        year = this?.year
    )

// Authors
private fun Author.toTopMangaDataAuthor() =
    TopMangaDataAuthors(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )

fun List<Author>?.toTopMangaDataAuthors(): List<TopMangaDataAuthors> =
    this?.map { it.toTopMangaDataAuthor() } ?: emptyList()

// Serializations
private fun Serialization.toTopMangaDataSerialization() =
    TopMangaDataSerializations(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )

fun List<Serialization>?.toTopMangaDataSerializations(): List<TopMangaDataSerializations> =
    this?.map { it.toTopMangaDataSerialization() } ?: emptyList()

// Genres
private fun Genre.toTopMangaDataGenre() =
    TopMangaDataGenres(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )

fun List<Genre>?.toTopMangaDataGenres(): List<TopMangaDataGenres> =
    this?.map { it.toTopMangaDataGenre() } ?: emptyList()

// Explicit Genres
private fun ExplicitGenre.toTopMangaDataExplicitGenre() =
    TopMangaDataExplicitGenres(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )

fun List<ExplicitGenre>?.toTopMangaDataExplicitGenres(): List<TopMangaDataExplicitGenres> =
    this?.map { it.toTopMangaDataExplicitGenre() } ?: emptyList()

// Themes
private fun Theme.toTopMangaDataTheme() =
    TopMangaDataThemes(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )

fun List<Theme>?.toTopMangaDataThemes(): List<TopMangaDataThemes> =
    this?.map { it.toTopMangaDataTheme() } ?: emptyList()

// Demographics
private fun Demographic.toTopMangaDataDemographic() =
    TopMangaDataDemographics(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )

fun List<Demographic>?.toTopMangaDataDemographics(): List<TopMangaDataDemographics> =
    this?.map { it.toTopMangaDataDemographic() } ?: emptyList()

// Collection list mapping
fun Collection<TopMangaData>?.toTopMangaList() =
    this?.map {
        it.toTopManga()
    } ?: listOf()