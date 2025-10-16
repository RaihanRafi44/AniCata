package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.season.year.AiredSeasonAnimeYear
import com.raihan.anicata.data.model.anime.season.year.BroadcastSeasonAnimeYear
import com.raihan.anicata.data.model.anime.season.year.DatePropFrom
import com.raihan.anicata.data.model.anime.season.year.DatePropTo
import com.raihan.anicata.data.model.anime.season.year.ImageFormatJpg
import com.raihan.anicata.data.model.anime.season.year.ImageFormatWebp
import com.raihan.anicata.data.model.anime.season.year.ImagesSeasonAnimeYear
import com.raihan.anicata.data.model.anime.season.year.PropSeasonAnimeYear
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import com.raihan.anicata.data.model.anime.season.year.SeasonYearDataDemographics
import com.raihan.anicata.data.model.anime.season.year.SeasonYearDataExplicitGenres
import com.raihan.anicata.data.model.anime.season.year.SeasonYearDataGenres
import com.raihan.anicata.data.model.anime.season.year.SeasonYearDataLicensors
import com.raihan.anicata.data.model.anime.season.year.SeasonYearDataProducers
import com.raihan.anicata.data.model.anime.season.year.SeasonYearDataStudios
import com.raihan.anicata.data.model.anime.season.year.SeasonYearDataThemes
import com.raihan.anicata.data.model.anime.season.year.TrailerSeasonAnimeYear
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Aired
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Broadcast
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Demographic
import com.raihan.anicata.data.source.network.model.anime.seasons.year.ExplicitGenre
import com.raihan.anicata.data.source.network.model.anime.seasons.year.From
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Genre
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Images
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Jpg
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Licensor
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Producer
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Prop
import com.raihan.anicata.data.source.network.model.anime.seasons.year.SeasonYearData
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Studio
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Theme
import com.raihan.anicata.data.source.network.model.anime.seasons.year.To
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Trailer
import com.raihan.anicata.data.source.network.model.anime.seasons.year.Webp

fun SeasonYearData?.toSeasonYearAnime() =
    SeasonAnimeYear(
        id = this?.malId ?: 0,
        url = this?.url.orEmpty(),
        images = this?.images.toSeasonYearImages(),
        trailer = this?.trailer.toSeasonYearTrailer(),
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
        aired = this?.aired.toSeasonYearAired(),
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
        broadcast = this?.broadcast.toSeasonYearBroadcast(),
        producers = this?.producers.toSeasonYearDataProducers(),
        licensors = this?.licensors.toSeasonYearDataLicensors(),
        studios = this?.studios.toSeasonYearDataStudios(),
        genres = this?.genres.toSeasonYearDataGenres(),
        explicitGenres = this?.explicitGenres.toSeasonYearDataExplicitGenres(),
        themes = this?.themes.toSeasonYearDataThemes(),
        demographics = this?.demographics.toSeasonYearDataDemographics()
    )

fun Images?.toSeasonYearImages() =
    ImagesSeasonAnimeYear(
        jpg = this?.jpg.toSeasonYearImageSetJpg(),
        webp = this?.webp.toSeasonYearImageSetWebp()
    )

fun Jpg?.toSeasonYearImageSetJpg() =
    ImageFormatJpg(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Webp?.toSeasonYearImageSetWebp() =
    ImageFormatWebp(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Trailer?.toSeasonYearTrailer() =
    TrailerSeasonAnimeYear(
        youtubeId = this?.youtubeId.orEmpty(),
        url = this?.url.orEmpty(),
        embedUrl = this?.embedUrl.orEmpty()
    )

fun Aired?.toSeasonYearAired() =
    AiredSeasonAnimeYear(
        from = this?.from.orEmpty(),
        to = this?.to.orEmpty(),
        prop = this?.prop.toSeasonYearProp()
    )

fun Prop?.toSeasonYearProp() =
    PropSeasonAnimeYear(
        from = this?.from.toSeasonYearDateInfoFrom(),
        to = this?.to.toSeasonYearDateInfoTo(),
        string = this?.string.orEmpty()
    )

fun From?.toSeasonYearDateInfoFrom() =
    DatePropFrom(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun To?.toSeasonYearDateInfoTo() =
    DatePropTo(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun Broadcast?.toSeasonYearBroadcast() =
    BroadcastSeasonAnimeYear(
        day = this?.day.orEmpty(),
        time = this?.time.orEmpty(),
        timezone = this?.timezone.orEmpty(),
        string = this?.string.orEmpty()
    )

// Producer
private fun Producer.toSeasonYearDataProducer() =
    SeasonYearDataProducers(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Producer>?.toSeasonYearDataProducers(): List<SeasonYearDataProducers> =
    this?.map { it.toSeasonYearDataProducer() } ?: emptyList()

// Licensor
private fun Licensor.toSeasonYearDataLicensor() =
    SeasonYearDataLicensors(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Licensor>?.toSeasonYearDataLicensors(): List<SeasonYearDataLicensors> =
    this?.map { it.toSeasonYearDataLicensor() } ?: emptyList()

// Studio
private fun Studio.toSeasonYearDataStudio() =
    SeasonYearDataStudios(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Studio>?.toSeasonYearDataStudios(): List<SeasonYearDataStudios> =
    this?.map { it.toSeasonYearDataStudio() } ?: emptyList()

// Genre
private fun Genre.toSeasonYearDataGenre() =
    SeasonYearDataGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Genre>?.toSeasonYearDataGenres(): List<SeasonYearDataGenres> =
    this?.map { it.toSeasonYearDataGenre() } ?: emptyList()

// Explicit Genre
private fun ExplicitGenre.toSeasonYearDataExplicitGenre() =
    SeasonYearDataExplicitGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<ExplicitGenre>?.toSeasonYearDataExplicitGenres(): List<SeasonYearDataExplicitGenres> =
    this?.map { it.toSeasonYearDataExplicitGenre() } ?: emptyList()

// Theme
private fun Theme.toSeasonYearDataTheme() =
    SeasonYearDataThemes(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Theme>?.toSeasonYearDataThemes(): List<SeasonYearDataThemes> =
    this?.map { it.toSeasonYearDataTheme() } ?: emptyList()

// Demographic
private fun Demographic.toSeasonYearDataDemographic() =
    SeasonYearDataDemographics(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Demographic>?.toSeasonYearDataDemographics(): List<SeasonYearDataDemographics> =
    this?.map { it.toSeasonYearDataDemographic() } ?: emptyList()

fun Collection<SeasonYearData>?.toSeasonYearAnimeList() =
    this?.map {
        it.toSeasonYearAnime()
    } ?: listOf()
