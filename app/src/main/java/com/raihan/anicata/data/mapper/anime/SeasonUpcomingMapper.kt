package com.raihan.anicata.data.mapper.anime


import com.raihan.anicata.data.model.anime.season.upcoming.AiredSeasonAnimeUpcoming
import com.raihan.anicata.data.model.anime.season.upcoming.BroadcastSeasonAnimeUpcoming
import com.raihan.anicata.data.model.anime.season.upcoming.DatePropFrom
import com.raihan.anicata.data.model.anime.season.upcoming.DatePropTo
import com.raihan.anicata.data.model.anime.season.upcoming.ImageFormatJpg
import com.raihan.anicata.data.model.anime.season.upcoming.ImageFormatWebp
import com.raihan.anicata.data.model.anime.season.upcoming.ImagesSeasonAnimeUpcoming
import com.raihan.anicata.data.model.anime.season.upcoming.PropSeasonAnimeUpcoming
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonAnimeUpcoming
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonUpcomingDataDemographics
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonUpcomingDataExplicitGenres
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonUpcomingDataGenres
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonUpcomingDataLicensors
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonUpcomingDataProducers
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonUpcomingDataStudios
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonUpcomingDataThemes
import com.raihan.anicata.data.model.anime.season.upcoming.TrailerSeasonAnimeUpcoming
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Aired
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Broadcast
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Demographic
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.ExplicitGenre
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.From
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Genre
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Images
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Jpg
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Licensor
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Producer
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Prop
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.SeasonUpcomingData
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Studio
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Theme
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.To
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Trailer
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.Webp
import kotlin.collections.orEmpty
import kotlin.text.orEmpty

fun SeasonUpcomingData?.toSeasonUpcomingAnime() =
    SeasonAnimeUpcoming(
        id = this?.malId ?: 0,
        url = this?.url.orEmpty(),
        images = this?.images.toSeasonUpcomingImages(),
        trailer = this?.trailer.toSeasonUpcomingTrailer(),
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
        aired = this?.aired.toSeasonUpcomingAired(),
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
        broadcast = this?.broadcast.toSeasonUpcomingBroadcast(),
        producers = this?.producers.toSeasonUpcomingDataProducers(),
        licensors = this?.licensors.toSeasonUpcomingDataLicensors(),
        studios = this?.studios.toSeasonUpcomingDataStudios(),
        genres = this?.genres.toSeasonUpcomingDataGenres(),
        explicitGenres = this?.explicitGenres.toSeasonUpcomingDataExplicitGenres(),
        themes = this?.themes.toSeasonUpcomingDataThemes(),
        demographics = this?.demographics.toSeasonUpcomingDataDemographics()
    )

fun Images?.toSeasonUpcomingImages() =
    ImagesSeasonAnimeUpcoming(
        jpg = this?.jpg.toSeasonUpcomingImageSetJpg(),
        webp = this?.webp.toSeasonUpcomingImageSetWebp()
    )

fun Jpg?.toSeasonUpcomingImageSetJpg() =
    ImageFormatJpg(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Webp?.toSeasonUpcomingImageSetWebp() =
    ImageFormatWebp(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Trailer?.toSeasonUpcomingTrailer() =
    TrailerSeasonAnimeUpcoming(
        youtubeId = this?.youtubeId.orEmpty(),
        url = this?.url.orEmpty(),
        embedUrl = this?.embedUrl.orEmpty()
    )

fun Aired?.toSeasonUpcomingAired() =
    AiredSeasonAnimeUpcoming(
        from = this?.from.orEmpty(),
        to = this?.to.orEmpty(),
        prop = this?.prop.toSeasonUpcomingProp()
    )

fun Prop?.toSeasonUpcomingProp() =
    PropSeasonAnimeUpcoming(
        from = this?.from.toSeasonUpcomingDateInfoFrom(),
        to = this?.to.toSeasonUpcomingDateInfoTo(),
        string = this?.string.orEmpty()
    )

fun From?.toSeasonUpcomingDateInfoFrom() =
    DatePropFrom(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun To?.toSeasonUpcomingDateInfoTo() =
    DatePropTo(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun Broadcast?.toSeasonUpcomingBroadcast() =
    BroadcastSeasonAnimeUpcoming(
        day = this?.day.orEmpty(),
        time = this?.time.orEmpty(),
        timezone = this?.timezone.orEmpty(),
        string = this?.string.orEmpty()
    )

// Producer
private fun Producer.toSeasonUpcomingDataProducer() =
    SeasonUpcomingDataProducers(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Producer>?.toSeasonUpcomingDataProducers(): List<SeasonUpcomingDataProducers> =
    this?.map { it.toSeasonUpcomingDataProducer() } ?: emptyList()

// Licensor
private fun Licensor.toSeasonUpcomingDataLicensor() =
    SeasonUpcomingDataLicensors(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Licensor>?.toSeasonUpcomingDataLicensors(): List<SeasonUpcomingDataLicensors> =
    this?.map { it.toSeasonUpcomingDataLicensor() } ?: emptyList()

// Studio
private fun Studio.toSeasonUpcomingDataStudio() =
    SeasonUpcomingDataStudios(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Studio>?.toSeasonUpcomingDataStudios(): List<SeasonUpcomingDataStudios> =
    this?.map { it.toSeasonUpcomingDataStudio() } ?: emptyList()

// Genre
private fun Genre.toSeasonUpcomingDataGenre() =
    SeasonUpcomingDataGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Genre>?.toSeasonUpcomingDataGenres(): List<SeasonUpcomingDataGenres> =
    this?.map { it.toSeasonUpcomingDataGenre() } ?: emptyList()

// Explicit Genre
private fun ExplicitGenre.toSeasonUpcomingDataExplicitGenre() =
    SeasonUpcomingDataExplicitGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<ExplicitGenre>?.toSeasonUpcomingDataExplicitGenres(): List<SeasonUpcomingDataExplicitGenres> =
    this?.map { it.toSeasonUpcomingDataExplicitGenre() } ?: emptyList()

// Theme
private fun Theme.toSeasonUpcomingDataTheme() =
    SeasonUpcomingDataThemes(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Theme>?.toSeasonUpcomingDataThemes(): List<SeasonUpcomingDataThemes> =
    this?.map { it.toSeasonUpcomingDataTheme() } ?: emptyList()

// Demographic
private fun Demographic.toSeasonUpcomingDataDemographic() =
    SeasonUpcomingDataDemographics(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Demographic>?.toSeasonUpcomingDataDemographics(): List<SeasonUpcomingDataDemographics> =
    this?.map { it.toSeasonUpcomingDataDemographic() } ?: emptyList()

fun Collection<SeasonUpcomingData>?.toSeasonUpcomingAnimeList() =
    this?.map {
        it.toSeasonUpcomingAnime()
    } ?: listOf()
