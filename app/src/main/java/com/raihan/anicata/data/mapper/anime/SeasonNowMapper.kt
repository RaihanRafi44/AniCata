package com.raihan.anicata.data.mapper.anime


import com.raihan.anicata.data.model.anime.season.now.AiredSeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.now.BroadcastSeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.now.DatePropFrom
import com.raihan.anicata.data.model.anime.season.now.DatePropTo
import com.raihan.anicata.data.model.anime.season.now.ImageFormatJpg
import com.raihan.anicata.data.model.anime.season.now.ImageFormatWebp
import com.raihan.anicata.data.model.anime.season.now.ImagesSeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.now.PropSeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.now.SeasonNowDataDemographics
import com.raihan.anicata.data.model.anime.season.now.SeasonNowDataExplicitGenres
import com.raihan.anicata.data.model.anime.season.now.SeasonNowDataGenres
import com.raihan.anicata.data.model.anime.season.now.SeasonNowDataLicensors
import com.raihan.anicata.data.model.anime.season.now.SeasonNowDataProducers
import com.raihan.anicata.data.model.anime.season.now.SeasonNowDataStudios
import com.raihan.anicata.data.model.anime.season.now.SeasonNowDataThemes
import com.raihan.anicata.data.model.anime.season.now.TrailerSeasonAnimeNow
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Aired
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Broadcast
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Demographic
import com.raihan.anicata.data.source.network.model.anime.seasons.now.ExplicitGenre
import com.raihan.anicata.data.source.network.model.anime.seasons.now.From
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Genre
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Images
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Jpg
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Licensor
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Producer
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Prop
import com.raihan.anicata.data.source.network.model.anime.seasons.now.SeasonNowData
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Studio
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Theme
import com.raihan.anicata.data.source.network.model.anime.seasons.now.To
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Trailer
import com.raihan.anicata.data.source.network.model.anime.seasons.now.Webp
import kotlin.collections.orEmpty
import kotlin.text.orEmpty

fun SeasonNowData?.toSeasonNowAnime() =
    SeasonAnimeNow(
        id = this?.malId ?: 0,
        url = this?.url.orEmpty(),
        images = this?.images.toSeasonNowImages(),
        trailer = this?.trailer.toSeasonNowTrailer(),
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
        aired = this?.aired.toSeasonNowAired(),
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
        broadcast = this?.broadcast.toSeasonNowBroadcast(),
        producers = this?.producers.toSeasonNowDataProducers(),
        licensors = this?.licensors.toSeasonNowDataLicensors(),
        studios = this?.studios.toSeasonNowDataStudios(),
        genres = this?.genres.toSeasonNowDataGenres(),
        explicitGenres = this?.explicitGenres.toSeasonNowDataExplicitGenres(),
        themes = this?.themes.toSeasonNowDataThemes(),
        demographics = this?.demographics.toSeasonNowDataDemographics()
    )

fun Images?.toSeasonNowImages() =
    ImagesSeasonAnimeNow(
        jpg = this?.jpg.toSeasonNowImageSetJpg(),
        webp = this?.webp.toSeasonNowImageSetWebp()
    )

fun Jpg?.toSeasonNowImageSetJpg() =
    ImageFormatJpg(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Webp?.toSeasonNowImageSetWebp() =
    ImageFormatWebp(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Trailer?.toSeasonNowTrailer() =
    TrailerSeasonAnimeNow(
        youtubeId = this?.youtubeId.orEmpty(),
        url = this?.url.orEmpty(),
        embedUrl = this?.embedUrl.orEmpty()
    )

fun Aired?.toSeasonNowAired() =
    AiredSeasonAnimeNow(
        from = this?.from.orEmpty(),
        to = this?.to.orEmpty(),
        prop = this?.prop.toSeasonNowProp()
    )

fun Prop?.toSeasonNowProp() =
    PropSeasonAnimeNow(
        from = this?.from.toSeasonNowDateInfoFrom(),
        to = this?.to.toSeasonNowDateInfoTo(),
        string = this?.string.orEmpty()
    )

fun From?.toSeasonNowDateInfoFrom() =
    DatePropFrom(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun To?.toSeasonNowDateInfoTo() =
    DatePropTo(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun Broadcast?.toSeasonNowBroadcast() =
    BroadcastSeasonAnimeNow(
        day = this?.day.orEmpty(),
        time = this?.time.orEmpty(),
        timezone = this?.timezone.orEmpty(),
        string = this?.string.orEmpty()
    )

// Producer
private fun Producer.toSeasonNowDataProducer() =
    SeasonNowDataProducers(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Producer>?.toSeasonNowDataProducers(): List<SeasonNowDataProducers> =
    this?.map { it.toSeasonNowDataProducer() } ?: emptyList()

// Licensor
private fun Licensor.toSeasonNowDataLicensor() =
    SeasonNowDataLicensors(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Licensor>?.toSeasonNowDataLicensors(): List<SeasonNowDataLicensors> =
    this?.map { it.toSeasonNowDataLicensor() } ?: emptyList()

// Studio
private fun Studio.toSeasonNowDataStudio() =
    SeasonNowDataStudios(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Studio>?.toSeasonNowDataStudios(): List<SeasonNowDataStudios> =
    this?.map { it.toSeasonNowDataStudio() } ?: emptyList()

// Genre
private fun Genre.toSeasonNowDataGenre() =
    SeasonNowDataGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Genre>?.toSeasonNowDataGenres(): List<SeasonNowDataGenres> =
    this?.map { it.toSeasonNowDataGenre() } ?: emptyList()

// Explicit Genre
private fun ExplicitGenre.toSeasonNowDataExplicitGenre() =
    SeasonNowDataExplicitGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<ExplicitGenre>?.toSeasonNowDataExplicitGenres(): List<SeasonNowDataExplicitGenres> =
    this?.map { it.toSeasonNowDataExplicitGenre() } ?: emptyList()

// Theme
private fun Theme.toSeasonNowDataTheme() =
    SeasonNowDataThemes(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Theme>?.toSeasonNowDataThemes(): List<SeasonNowDataThemes> =
    this?.map { it.toSeasonNowDataTheme() } ?: emptyList()

// Demographic
private fun Demographic.toSeasonNowDataDemographic() =
    SeasonNowDataDemographics(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Demographic>?.toSeasonNowDataDemographics(): List<SeasonNowDataDemographics> =
    this?.map { it.toSeasonNowDataDemographic() } ?: emptyList()

fun Collection<SeasonNowData>?.toSeasonNowAnimeList() =
    this?.map {
        it.toSeasonNowAnime()
    } ?: listOf()