package com.raihan.anicata.data.mapper.manga

import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.data.model.manga.full.MangaFullDateFrom
import com.raihan.anicata.data.model.manga.full.MangaFullDateTo
import com.raihan.anicata.data.model.manga.full.MangaFullEntityAuthors
import com.raihan.anicata.data.model.manga.full.MangaFullEntityDemographics
import com.raihan.anicata.data.model.manga.full.MangaFullEntityExplicitGenres
import com.raihan.anicata.data.model.manga.full.MangaFullEntityGenres
import com.raihan.anicata.data.model.manga.full.MangaFullEntityRelation
import com.raihan.anicata.data.model.manga.full.MangaFullEntitySerialization
import com.raihan.anicata.data.model.manga.full.MangaFullEntityThemes
import com.raihan.anicata.data.model.manga.full.MangaFullExternal
import com.raihan.anicata.data.model.manga.full.MangaFullImages
import com.raihan.anicata.data.model.manga.full.MangaFullPublished
import com.raihan.anicata.data.model.manga.full.MangaFullPublishedProp
import com.raihan.anicata.data.model.manga.full.MangaFullRelation
import com.raihan.anicata.data.model.manga.full.MangaImageTypeJpg
import com.raihan.anicata.data.model.manga.full.MangaImageTypeWebp
import com.raihan.anicata.data.source.network.model.manga.full.Author
import com.raihan.anicata.data.source.network.model.manga.full.Demographic
import com.raihan.anicata.data.source.network.model.manga.full.Entry
import com.raihan.anicata.data.source.network.model.manga.full.ExplicitGenre
import com.raihan.anicata.data.source.network.model.manga.full.External
import com.raihan.anicata.data.source.network.model.manga.full.From
import com.raihan.anicata.data.source.network.model.manga.full.Genre
import com.raihan.anicata.data.source.network.model.manga.full.Images
import com.raihan.anicata.data.source.network.model.manga.full.Jpg
import com.raihan.anicata.data.source.network.model.manga.full.MangaDetailFullData
import com.raihan.anicata.data.source.network.model.manga.full.Prop
import com.raihan.anicata.data.source.network.model.manga.full.Published
import com.raihan.anicata.data.source.network.model.manga.full.Relation
import com.raihan.anicata.data.source.network.model.manga.full.Serialization
import com.raihan.anicata.data.source.network.model.manga.full.Theme
import com.raihan.anicata.data.source.network.model.manga.full.To
import com.raihan.anicata.data.source.network.model.manga.full.Webp

fun MangaDetailFullData?.toDetailFullManga() =
    MangaDetailFull(
        id = this?.malId ?: 0,
        url = this?.url.orEmpty(),
        images = this?.images.toMangaFullImages(),
        approved = this?.approved ?: false,
        title = this?.title.orEmpty(),
        titleEnglish = this?.titleEnglish,
        titleJapanese = this?.titleJapanese,
        titleSynonyms = this?.titleSynonyms ?: emptyList(),
        type = this?.type.orEmpty(),
        chapters = this?.chapters,
        volumes = this?.volumes,
        status = this?.status.orEmpty(),
        publishing = this?.publishing ?: false,
        published = this?.published.toMangaFullPublished(),
        score = this?.score,
        scoredBy = this?.scoredBy,
        rank = this?.rank,
        popularity = this?.popularity,
        members = this?.members,
        favorites = this?.favorites,
        synopsis = this?.synopsis,
        background = this?.background,
        authors = this?.authors.toMangaFullEntityAuthors(),
        serializations = this?.serializations.toMangaFullEntitySerializations(),
        genres = this?.genres.toMangaFullEntityGenres(),
        explicitGenres = this?.explicitGenres.toMangaFullEntityExplicitGenres(),
        themes = this?.themes.toMangaFullEntityThemes(),
        demographics = this?.demographics.toMangaFullEntityDemographics(),
        relations = this?.relations.toMangaFullRelations(),
        external = this?.external.toMangaFullExternal()
    )

// Images
fun Images?.toMangaFullImages() =
    MangaFullImages(
        jpg = this?.jpg.toMangaFullImageSetJpg(),
        webp = this?.webp.toMangaFullImageSetWebp()
    )

fun Jpg?.toMangaFullImageSetJpg() =
    MangaImageTypeJpg(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Webp?.toMangaFullImageSetWebp() =
    MangaImageTypeWebp(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

// Published
fun Published?.toMangaFullPublished() =
    MangaFullPublished(
        from = this?.from,
        to = this?.to,
        prop = this?.prop.toMangaFullPublishedProp()
    )

fun Prop?.toMangaFullPublishedProp() =
    MangaFullPublishedProp(
        from = this?.from.toMangaFullDateFrom(),
        to = this?.to.toMangaFullDateTo(),
        string = this?.string
    )

fun From?.toMangaFullDateFrom() =
    MangaFullDateFrom(
        day = this?.day,
        month = this?.month,
        year = this?.year
    )

fun To?.toMangaFullDateTo() =
    MangaFullDateTo(
        day = this?.day,
        month = this?.month,
        year = this?.year
    )

// Authors
private fun Author.toMangaFullEntityAuthor() =
    MangaFullEntityAuthors(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )

fun List<Author>?.toMangaFullEntityAuthors(): List<MangaFullEntityAuthors> =
    this?.map { it.toMangaFullEntityAuthor() } ?: emptyList()

// Serializations
private fun Serialization.toMangaFullEntitySerialization() =
    MangaFullEntitySerialization(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )

fun List<Serialization>?.toMangaFullEntitySerializations(): List<MangaFullEntitySerialization> =
    this?.map { it.toMangaFullEntitySerialization() } ?: emptyList()

// Genres
private fun Genre.toMangaFullEntityGenre() =
    MangaFullEntityGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )

fun List<Genre>?.toMangaFullEntityGenres(): List<MangaFullEntityGenres> =
    this?.map { it.toMangaFullEntityGenre() } ?: emptyList()

// Explicit Genres
private fun ExplicitGenre.toMangaFullEntityExplicitGenre() =
    MangaFullEntityExplicitGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )

fun List<ExplicitGenre>?.toMangaFullEntityExplicitGenres(): List<MangaFullEntityExplicitGenres> =
    this?.map { it.toMangaFullEntityExplicitGenre() } ?: emptyList()

// Themes
private fun Theme.toMangaFullEntityTheme() =
    MangaFullEntityThemes(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )

fun List<Theme>?.toMangaFullEntityThemes(): List<MangaFullEntityThemes> =
    this?.map { it.toMangaFullEntityTheme() } ?: emptyList()

// Demographics
private fun Demographic.toMangaFullEntityDemographic() =
    MangaFullEntityDemographics(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )

fun List<Demographic>?.toMangaFullEntityDemographics(): List<MangaFullEntityDemographics> =
    this?.map { it.toMangaFullEntityDemographic() } ?: emptyList()

// Relations
private fun Relation.toMangaFullRelation() =
    MangaFullRelation(
        relation = this.relation.orEmpty(),
        entry = this.entry.toMangaFullEntityRelationList()
    )

fun List<Relation>?.toMangaFullRelations(): List<MangaFullRelation> =
    this?.map { it.toMangaFullRelation() } ?: emptyList()

private fun Entry.toMangaFullEntityRelation() =
    MangaFullEntityRelation(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )

fun List<Entry>?.toMangaFullEntityRelationList(): List<MangaFullEntityRelation> =
    this?.map { it.toMangaFullEntityRelation() } ?: emptyList()

// External links
private fun External.toMangaFullExternal() =
    MangaFullExternal(
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )

fun List<External>?.toMangaFullExternal(): List<MangaFullExternal> =
    this?.map { it.toMangaFullExternal() }  ?: emptyList()

fun Collection<MangaDetailFullData>?.toDetailManga() =
    this?.map {
        it.toDetailFullManga()
    }