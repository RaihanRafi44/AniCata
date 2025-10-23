package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.full.AiredAnime
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.anime.full.DateInfoAnimeFrom
import com.raihan.anicata.data.model.anime.full.DateInfoAnimeTo
import com.raihan.anicata.data.model.anime.full.ImageSetJpg
import com.raihan.anicata.data.model.anime.full.ImageSetWebp
import com.raihan.anicata.data.model.anime.full.ImagesDetail
import com.raihan.anicata.data.model.anime.full.MalUrlAnimeDemographics
import com.raihan.anicata.data.model.anime.full.MalUrlAnimeEntryRelation
import com.raihan.anicata.data.model.anime.full.MalUrlAnimeExplicitGenres
import com.raihan.anicata.data.model.anime.full.MalUrlAnimeGenres
import com.raihan.anicata.data.model.anime.full.MalUrlAnimeLicensors
import com.raihan.anicata.data.model.anime.full.MalUrlAnimeProducers
import com.raihan.anicata.data.model.anime.full.MalUrlAnimeStudios
import com.raihan.anicata.data.model.anime.full.MalUrlAnimeThemes
import com.raihan.anicata.data.model.anime.full.PropAnime
import com.raihan.anicata.data.model.anime.full.RelationAnime
import com.raihan.anicata.data.model.anime.full.TrailerAnime
import com.raihan.anicata.data.source.network.model.anime.full.Aired
import com.raihan.anicata.data.source.network.model.anime.full.AnimeDetailFullData
import com.raihan.anicata.data.source.network.model.anime.full.Demographic
import com.raihan.anicata.data.source.network.model.anime.full.Entry
import com.raihan.anicata.data.source.network.model.anime.full.ExplicitGenre
import com.raihan.anicata.data.source.network.model.anime.full.From
import com.raihan.anicata.data.source.network.model.anime.full.Genre
import com.raihan.anicata.data.source.network.model.anime.full.Images
import com.raihan.anicata.data.source.network.model.anime.full.Jpg
import com.raihan.anicata.data.source.network.model.anime.full.Licensor
import com.raihan.anicata.data.source.network.model.anime.full.Producer
import com.raihan.anicata.data.source.network.model.anime.full.Prop
import com.raihan.anicata.data.source.network.model.anime.full.Relation
import com.raihan.anicata.data.source.network.model.anime.full.Studio
import com.raihan.anicata.data.source.network.model.anime.full.ThemeX
import com.raihan.anicata.data.source.network.model.anime.full.To
import com.raihan.anicata.data.source.network.model.anime.full.Trailer
import com.raihan.anicata.data.source.network.model.anime.full.Webp

fun AnimeDetailFullData?.toDetailFullAnime() =
    AnimeData(
        id = this?.malId ?: 0,
        url = this?.url.orEmpty(),
        images = this?.images.toDetailImages(),
        trailer = this?.trailer.toDetailTrailer(),
        approved = this?.approved ?: false,
        title = this?.title.orEmpty(),
        titleEnglish = this?.titleEnglish.orEmpty(),
        titleJapanese = this?.titleJapanese.orEmpty(),
        titleSynonyms = this?.titleSynonyms.orEmpty(),
        type = this?.type.orEmpty(),
        source = this?.source.orEmpty(),
        episodes = this?.episodes ?: 0,
        status = this?.status.orEmpty(),
        airing = this?.airing ?: false,
        aired = this?.aired.toDetailAired(),
        duration = this?.duration.orEmpty(),
        rating = this?.rating.orEmpty(),
        score = this?.score ?: 0.0,
        scoredBy = this?.scoredBy ?: 0,
        rank = this?.rank ?: 0,
        popularity = this?.popularity ?: 0,
        members = this?.members ?: 0,
        favorites = this?.favorites ?: 0,
        synopsis = this?.synopsis.orEmpty(),
        background = this?.background.orEmpty(),
        season = this?.season.orEmpty(),
        year = this?.year ?: 0,
        producers = this?.producers.toDetailMalUrlProducers(),
        licensors = this?.licensors.toDetailMalUrlLicensors(),
        studios = this?.studios.toDetailMalUrlStudios(),
        genres = this?.genres.toDetailMalUrlGenres(),
        explicitGenres = this?.explicitGenres.toDetailMalUrlExplicitGenres(),
        themes = this?.themes.toDetailMalUrlThemes(),
        demographics = this?.demographics.toDetailMalUrlDemographics(),
        relations = this?.relations.toDetailRelations()
    )

fun Images?.toDetailImages() =
    ImagesDetail(
        jpg = this?.jpg.toDetailImageSetJpg(),
        webp = this?.webp.toDetailImageSetWebp()
    )

fun Jpg?.toDetailImageSetJpg() =
    ImageSetJpg(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Webp?.toDetailImageSetWebp() =
    ImageSetWebp(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Trailer?.toDetailTrailer() =
    TrailerAnime(
        youtubeId = this?.youtubeId.orEmpty(),
        url = this?.url.orEmpty(),
        embedUrl = this?.embedUrl.orEmpty()
    )

fun Aired?.toDetailAired() =
    AiredAnime(
        from = this?.from.orEmpty(),
        to = this?.to.orEmpty(),
        prop = this?.prop.toDetailProp()
    )

fun Prop?.toDetailProp() =
    PropAnime(
        from = this?.from.toDetailDateInfoFrom(),
        to = this?.to.toDetailDateInfoTo(),
        string = this?.string.orEmpty()
    )

fun From?.toDetailDateInfoFrom() =
    DateInfoAnimeFrom(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun To?.toDetailDateInfoTo() =
    DateInfoAnimeTo(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

// Relation
private fun Relation.toDetailRelation() =
    RelationAnime(
        relation = this.relation.orEmpty(),
        entry = this.entry.toDetailMalUrlEntryRelations()
    )
fun List<Relation>?.toDetailRelations(): List<RelationAnime> =
    this?.map { it.toDetailRelation() } ?: emptyList()

// Producer
private fun Producer.toDetailMalUrlProducer() =
    MalUrlAnimeProducers(
        malId = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Producer>?.toDetailMalUrlProducers(): List<MalUrlAnimeProducers> =
    this?.map { it.toDetailMalUrlProducer() } ?: emptyList()

// Licensor
private fun Licensor.toDetailMalUrlLicensor() =
    MalUrlAnimeLicensors(
        malId = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Licensor>?.toDetailMalUrlLicensors(): List<MalUrlAnimeLicensors> =
    this?.map { it.toDetailMalUrlLicensor() } ?: emptyList()

// Studio
private fun Studio.toDetailMalUrlStudio() =
    MalUrlAnimeStudios(
        malId = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Studio>?.toDetailMalUrlStudios(): List<MalUrlAnimeStudios> =
    this?.map { it.toDetailMalUrlStudio() } ?: emptyList()

// Genre
private fun Genre.toDetailMalUrlGenre() =
    MalUrlAnimeGenres(
        malId = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Genre>?.toDetailMalUrlGenres(): List<MalUrlAnimeGenres> =
    this?.map { it.toDetailMalUrlGenre() } ?: emptyList()

// Explicit Genre
private fun ExplicitGenre.toDetailMalUrlExplicitGenre() =
    MalUrlAnimeExplicitGenres(
        malId = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<ExplicitGenre>?.toDetailMalUrlExplicitGenres(): List<MalUrlAnimeExplicitGenres> =
    this?.map { it.toDetailMalUrlExplicitGenre() } ?: emptyList()

// Theme
private fun ThemeX.toDetailMalUrlTheme() =
    MalUrlAnimeThemes(
        malId = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<ThemeX>?.toDetailMalUrlThemes(): List<MalUrlAnimeThemes> =
    this?.map { it.toDetailMalUrlTheme() } ?: emptyList()

// Demographic
private fun Demographic.toDetailMalUrlDemographic() =
    MalUrlAnimeDemographics(
        malId = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Demographic>?.toDetailMalUrlDemographics(): List<MalUrlAnimeDemographics> =
    this?.map { it.toDetailMalUrlDemographic() } ?: emptyList()

// Entry Relation
private fun Entry.toDetailMalUrlEntryRelation() =
    MalUrlAnimeEntryRelation(
        malId = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Entry>?.toDetailMalUrlEntryRelations(): List<MalUrlAnimeEntryRelation> =
    this?.map { it.toDetailMalUrlEntryRelation() } ?: emptyList()

fun Collection<AnimeDetailFullData>?.toDetailAnime() =
    this?.map {
        it.toDetailFullAnime()
    } ?: listOf()

