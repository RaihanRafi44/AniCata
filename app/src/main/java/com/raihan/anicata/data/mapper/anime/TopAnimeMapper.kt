package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.top.AiredTopAnime
import com.raihan.anicata.data.model.anime.top.BroadcastTopAnime
import com.raihan.anicata.data.model.anime.top.DatePropFrom
import com.raihan.anicata.data.model.anime.top.DatePropTo
import com.raihan.anicata.data.model.anime.top.ImageFormatJpg
import com.raihan.anicata.data.model.anime.top.ImageFormatWebp
import com.raihan.anicata.data.model.anime.top.ImagesTopAnime
import com.raihan.anicata.data.model.anime.top.PropTopAnime
import com.raihan.anicata.data.model.anime.top.TopAnime
import com.raihan.anicata.data.model.anime.top.TopDataDemographics
import com.raihan.anicata.data.model.anime.top.TopDataExplicitGenres
import com.raihan.anicata.data.model.anime.top.TopDataGenres
import com.raihan.anicata.data.model.anime.top.TopDataLicensors
import com.raihan.anicata.data.model.anime.top.TopDataProducers
import com.raihan.anicata.data.model.anime.top.TopDataStudios
import com.raihan.anicata.data.model.anime.top.TopDataThemes
import com.raihan.anicata.data.model.anime.top.TrailerTopAnime
import com.raihan.anicata.data.source.network.model.anime.top.anime.Aired
import com.raihan.anicata.data.source.network.model.anime.top.anime.Broadcast
import com.raihan.anicata.data.source.network.model.anime.top.anime.Demographic
import com.raihan.anicata.data.source.network.model.anime.top.anime.ExplicitGenre
import com.raihan.anicata.data.source.network.model.anime.top.anime.From
import com.raihan.anicata.data.source.network.model.anime.top.anime.Genre
import com.raihan.anicata.data.source.network.model.anime.top.anime.Images
import com.raihan.anicata.data.source.network.model.anime.top.anime.Jpg
import com.raihan.anicata.data.source.network.model.anime.top.anime.Licensor
import com.raihan.anicata.data.source.network.model.anime.top.anime.Producer
import com.raihan.anicata.data.source.network.model.anime.top.anime.Prop
import com.raihan.anicata.data.source.network.model.anime.top.anime.Studio
import com.raihan.anicata.data.source.network.model.anime.top.anime.Theme
import com.raihan.anicata.data.source.network.model.anime.top.anime.To
import com.raihan.anicata.data.source.network.model.anime.top.anime.TopAnimeData
import com.raihan.anicata.data.source.network.model.anime.top.anime.Trailer
import com.raihan.anicata.data.source.network.model.anime.top.anime.Webp
import kotlin.collections.orEmpty
import kotlin.text.orEmpty

fun TopAnimeData?.toTopAnime() =
    TopAnime(
        id = this?.malId ?: 0,
        url = this?.url.orEmpty(),
        images = this?.images.toTopImages(),
        trailer = this?.trailer.toTopTrailer(),
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
        aired = this?.aired.toTopAired(),
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
        broadcast = this?.broadcast.toTopBroadcast(),
        producers = this?.producers.toTopDataProducers(),
        licensors = this?.licensors.toTopDataLicensors(),
        studios = this?.studios.toTopDataStudios(),
        genres = this?.genres.toTopDataGenres(),
        explicitGenres = this?.explicitGenres.toTopDataExplicitGenres(),
        themes = this?.themes.toTopDataThemes(),
        demographics = this?.demographics.toTopDataDemographics()
    )

fun Images?.toTopImages() =
    ImagesTopAnime(
        jpg = this?.jpg.toTopImageSetJpg(),
        webp = this?.webp.toTopImageSetWebp()
    )

fun Jpg?.toTopImageSetJpg() =
    ImageFormatJpg(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Webp?.toTopImageSetWebp() =
    ImageFormatWebp(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Trailer?.toTopTrailer() =
    TrailerTopAnime(
        youtubeId = this?.youtubeId.orEmpty(),
        url = this?.url.orEmpty(),
        embedUrl = this?.embedUrl.orEmpty()
    )

fun Aired?.toTopAired() =
    AiredTopAnime(
        from = this?.from.orEmpty(),
        to = this?.to.orEmpty(),
        prop = this?.prop.toTopProp()
    )

fun Prop?.toTopProp() =
    PropTopAnime(
        from = this?.from.toTopDateInfoFrom(),
        to = this?.to.toTopDateInfoTo(),
        string = this?.string.orEmpty()
    )

fun From?.toTopDateInfoFrom() =
    DatePropFrom(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun To?.toTopDateInfoTo() =
    DatePropTo(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun Broadcast?.toTopBroadcast() =
    BroadcastTopAnime(
        day = this?.day.orEmpty(),
        time = this?.time.orEmpty(),
        timezone = this?.timezone.orEmpty(),
        string = this?.string.orEmpty()
    )

// Producer
private fun Producer.toTopDataProducer() =
    TopDataProducers(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Producer>?.toTopDataProducers(): List<TopDataProducers> =
    this?.map { it.toTopDataProducer() } ?: emptyList()

// Licensor
private fun Licensor.toTopDataLicensor() =
    TopDataLicensors(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Licensor>?.toTopDataLicensors(): List<TopDataLicensors> =
    this?.map { it.toTopDataLicensor() } ?: emptyList()

// Studio
private fun Studio.toTopDataStudio() =
    TopDataStudios(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Studio>?.toTopDataStudios(): List<TopDataStudios> =
    this?.map { it.toTopDataStudio() } ?: emptyList()

// Genre
private fun Genre.toTopDataGenre() =
    TopDataGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Genre>?.toTopDataGenres(): List<TopDataGenres> =
    this?.map { it.toTopDataGenre() } ?: emptyList()

// Explicit Genre
private fun ExplicitGenre.toTopDataExplicitGenre() =
    TopDataExplicitGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<ExplicitGenre>?.toTopDataExplicitGenres(): List<TopDataExplicitGenres> =
    this?.map { it.toTopDataExplicitGenre() } ?: emptyList()

// Theme
private fun Theme.toTopDataTheme() =
    TopDataThemes(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Theme>?.toTopDataThemes(): List<TopDataThemes> =
    this?.map { it.toTopDataTheme() } ?: emptyList()

// Demographic
private fun Demographic.toTopDataDemographic() =
    TopDataDemographics(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Demographic>?.toTopDataDemographics(): List<TopDataDemographics> =
    this?.map { it.toTopDataDemographic() } ?: emptyList()

fun Collection<TopAnimeData>?.toTopAnimeList() =
    this?.map {
        it.toTopAnime()
    } ?: listOf()