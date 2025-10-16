package com.raihan.anicata.data.mapper.manga

import com.raihan.anicata.data.model.manga.search.DatePropFromSearchManga
import com.raihan.anicata.data.model.manga.search.DatePropToSearchManga
import com.raihan.anicata.data.model.manga.search.MangaImageTypeJpg
import com.raihan.anicata.data.model.manga.search.MangaImageTypeWebp
import com.raihan.anicata.data.model.manga.search.MangaImages
import com.raihan.anicata.data.model.manga.search.PublishedPropSearchManga
import com.raihan.anicata.data.model.manga.search.PublishedSearchManga
import com.raihan.anicata.data.model.manga.search.SearchManga
import com.raihan.anicata.data.model.manga.search.SearchMangaDataAuthors
import com.raihan.anicata.data.model.manga.search.SearchMangaDataDemographics
import com.raihan.anicata.data.model.manga.search.SearchMangaDataExplicitGenres
import com.raihan.anicata.data.model.manga.search.SearchMangaDataGenres
import com.raihan.anicata.data.model.manga.search.SearchMangaDataSerializations
import com.raihan.anicata.data.model.manga.search.SearchMangaDataThemes
import com.raihan.anicata.data.source.network.model.manga.search.Author
import com.raihan.anicata.data.source.network.model.manga.search.Demographic
import com.raihan.anicata.data.source.network.model.manga.search.ExplicitGenre
import com.raihan.anicata.data.source.network.model.manga.search.From
import com.raihan.anicata.data.source.network.model.manga.search.Genre
import com.raihan.anicata.data.source.network.model.manga.search.Images
import com.raihan.anicata.data.source.network.model.manga.search.Jpg
import com.raihan.anicata.data.source.network.model.manga.search.Prop
import com.raihan.anicata.data.source.network.model.manga.search.Published
import com.raihan.anicata.data.source.network.model.manga.search.SearchMangaData
import com.raihan.anicata.data.source.network.model.manga.search.Serialization
import com.raihan.anicata.data.source.network.model.manga.search.Theme
import com.raihan.anicata.data.source.network.model.manga.search.To
import com.raihan.anicata.data.source.network.model.manga.search.Webp

fun SearchMangaData?.toSearchManga() =
    SearchManga(
        id = this?.malId ?: 0,
        url = this?.url.orEmpty(),
        images = this?.images.toSearchMangaImages(),
        approved = this?.approved ?: false,
        title = this?.title.orEmpty(),
        titleEnglish = this?.titleEnglish,
        titleJapanese = this?.titleJapanese,
        type = this?.type,
        chapters = this?.chapters,
        volumes = this?.volumes,
        status = this?.status,
        publishing = this?.publishing ?: false,
        published = this?.published.toSearchMangaPublished(),
        score = this?.score,
        scoredBy = this?.scoredBy,
        rank = this?.rank,
        popularity = this?.popularity,
        members = this?.members,
        favorites = this?.favorites,
        synopsis = this?.synopsis,
        background = this?.background,
        authors = this?.authors.toSearchMangaDataAuthors(),
        serializations = this?.serializations.toSearchMangaDataSerializations(),
        genres = this?.genres.toSearchMangaDataGenres(),
        explicitGenres = this?.explicitGenres.toSearchMangaDataExplicitGenres(),
        themes = this?.themes.toSearchMangaDataThemes(),
        demographics = this?.demographics.toSearchMangaDataDemographics()
    )

// Images
fun Images?.toSearchMangaImages() =
    MangaImages(
        jpg = this?.jpg.toSearchMangaImageSetJpg(),
        webp = this?.webp.toSearchMangaImageSetWebp()
    )

fun Jpg?.toSearchMangaImageSetJpg() =
    MangaImageTypeJpg(
        imageUrl = this?.imageUrl,
        smallImageUrl = this?.smallImageUrl,
        largeImageUrl = this?.largeImageUrl
    )

fun Webp?.toSearchMangaImageSetWebp() =
    MangaImageTypeWebp(
        imageUrl = this?.imageUrl,
        smallImageUrl = this?.smallImageUrl,
        largeImageUrl = this?.largeImageUrl
    )

// Published
fun Published?.toSearchMangaPublished() =
    PublishedSearchManga(
        from = this?.from,
        to = this?.to,
        prop = this?.prop.toSearchMangaPublishedProp()
    )

fun Prop?.toSearchMangaPublishedProp() =
    PublishedPropSearchManga(
        from = this?.from.toSearchMangaDateInfoFrom(),
        to = this?.to.toSearchMangaDateInfoTo(),
        string = this?.string
    )

fun From?.toSearchMangaDateInfoFrom() =
    DatePropFromSearchManga(
        day = this?.day,
        month = this?.month,
        year = this?.year
    )

fun To?.toSearchMangaDateInfoTo() =
    DatePropToSearchManga(
        day = this?.day,
        month = this?.month,
        year = this?.year
    )

// Authors
private fun Author.toSearchMangaDataAuthor() =
    SearchMangaDataAuthors(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )
fun List<Author>?.toSearchMangaDataAuthors(): List<SearchMangaDataAuthors> =
    this?.map { it.toSearchMangaDataAuthor() } ?: emptyList()

// Serializations
private fun Serialization.toSearchMangaDataSerialization() =
    SearchMangaDataSerializations(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )
fun List<Serialization>?.toSearchMangaDataSerializations(): List<SearchMangaDataSerializations> =
    this?.map { it.toSearchMangaDataSerialization() } ?: emptyList()

// Genres
private fun Genre.toSearchMangaDataGenre() =
    SearchMangaDataGenres(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )
fun List<Genre>?.toSearchMangaDataGenres(): List<SearchMangaDataGenres> =
    this?.map { it.toSearchMangaDataGenre() } ?: emptyList()

// Explicit Genres
private fun ExplicitGenre.toSearchMangaDataExplicitGenre() =
    SearchMangaDataExplicitGenres(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )
fun List<ExplicitGenre>?.toSearchMangaDataExplicitGenres(): List<SearchMangaDataExplicitGenres> =
    this?.map { it.toSearchMangaDataExplicitGenre() } ?: emptyList()

// Themes
private fun Theme.toSearchMangaDataTheme() =
    SearchMangaDataThemes(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )
fun List<Theme>?.toSearchMangaDataThemes(): List<SearchMangaDataThemes> =
    this?.map { it.toSearchMangaDataTheme() } ?: emptyList()

// Demographics
private fun Demographic.toSearchMangaDataDemographic() =
    SearchMangaDataDemographics(
        id = this.malId ?: 0,
        type = this.type,
        name = this.name,
        url = this.url
    )
fun List<Demographic>?.toSearchMangaDataDemographics(): List<SearchMangaDataDemographics> =
    this?.map { it.toSearchMangaDataDemographic() } ?: emptyList()

// Collection list mapping
fun Collection<SearchMangaData>?.toSearchMangaList() =
    this?.map {
        it.toSearchManga()
    } ?: listOf()
