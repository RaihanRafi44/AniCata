package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.search.AiredSearchAnime
import com.raihan.anicata.data.model.anime.search.BroadcastSearchAnime
import com.raihan.anicata.data.model.anime.search.DatePropFrom
import com.raihan.anicata.data.model.anime.search.DatePropTo
import com.raihan.anicata.data.model.anime.search.ImageFormatJpg
import com.raihan.anicata.data.model.anime.search.ImageFormatWebp
import com.raihan.anicata.data.model.anime.search.ImagesSearchAnime
import com.raihan.anicata.data.model.anime.search.PropSearchAnime
import com.raihan.anicata.data.model.anime.search.SearchAnime
import com.raihan.anicata.data.model.anime.search.SearchDataDemographics
import com.raihan.anicata.data.model.anime.search.SearchDataExplicitGenres
import com.raihan.anicata.data.model.anime.search.SearchDataGenres
import com.raihan.anicata.data.model.anime.search.SearchDataLicensors
import com.raihan.anicata.data.model.anime.search.SearchDataProducers
import com.raihan.anicata.data.model.anime.search.SearchDataStudios
import com.raihan.anicata.data.model.anime.search.SearchDataThemes
import com.raihan.anicata.data.model.anime.search.TrailerSearchAnime
import com.raihan.anicata.data.source.network.model.anime.search.Aired
import com.raihan.anicata.data.source.network.model.anime.search.Broadcast
import com.raihan.anicata.data.source.network.model.anime.search.Demographic
import com.raihan.anicata.data.source.network.model.anime.search.ExplicitGenre
import com.raihan.anicata.data.source.network.model.anime.search.From
import com.raihan.anicata.data.source.network.model.anime.search.Genre
import com.raihan.anicata.data.source.network.model.anime.search.Images
import com.raihan.anicata.data.source.network.model.anime.search.Jpg
import com.raihan.anicata.data.source.network.model.anime.search.Licensor
import com.raihan.anicata.data.source.network.model.anime.search.Producer
import com.raihan.anicata.data.source.network.model.anime.search.Prop
import com.raihan.anicata.data.source.network.model.anime.search.SearchAnimeData
import com.raihan.anicata.data.source.network.model.anime.search.Studio
import com.raihan.anicata.data.source.network.model.anime.search.Theme
import com.raihan.anicata.data.source.network.model.anime.search.To
import com.raihan.anicata.data.source.network.model.anime.search.Trailer
import com.raihan.anicata.data.source.network.model.anime.search.Webp
import kotlin.collections.orEmpty
import kotlin.text.orEmpty

fun SearchAnimeData?.toSearchAnime() =
    SearchAnime(
        id = this?.malId ?: 0,
        url = this?.url.orEmpty(),
        images = this?.images.toSearchImages(),
        trailer = this?.trailer.toSearchTrailer(),
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
        aired = this?.aired.toSearchAired(),
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
        broadcast = this?.broadcast.toSearchBroadcast(),
        producers = this?.producers.toSearchDataProducers(),
        licensors = this?.licensors.toSearchDataLicensors(),
        studios = this?.studios.toSearchDataStudios(),
        genres = this?.genres.toSearchDataGenres(),
        explicitGenres = this?.explicitGenres.toSearchDataExplicitGenres(),
        themes = this?.themes.toSearchDataThemes(),
        demographics = this?.demographics.toSearchDataDemographics()
    )

fun Images?.toSearchImages() =
    ImagesSearchAnime(
        jpg = this?.jpg.toSearchImageSetJpg(),
        webp = this?.webp.toSearchImageSetWebp()
    )

fun Jpg?.toSearchImageSetJpg() =
    ImageFormatJpg(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Webp?.toSearchImageSetWebp() =
    ImageFormatWebp(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty(),
        largeImageUrl = this?.largeImageUrl.orEmpty()
    )

fun Trailer?.toSearchTrailer() =
    TrailerSearchAnime(
        youtubeId = this?.youtubeId.orEmpty(),
        url = this?.url.orEmpty(),
        embedUrl = this?.embedUrl.orEmpty()
    )

fun Aired?.toSearchAired() =
    AiredSearchAnime(
        from = this?.from.orEmpty(),
        to = this?.to.orEmpty(),
        prop = this?.prop.toSearchProp()
    )

fun Prop?.toSearchProp() =
    PropSearchAnime(
        from = this?.from.toSearchDateInfoFrom(),
        to = this?.to.toSearchDateInfoTo(),
        string = this?.string.orEmpty()
    )

fun From?.toSearchDateInfoFrom() =
    DatePropFrom(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun To?.toSearchDateInfoTo() =
    DatePropTo(
        day = this?.day ?: 0,
        month = this?.month ?: 0,
        year = this?.year ?: 0
    )

fun Broadcast?.toSearchBroadcast() =
    BroadcastSearchAnime(
        day = this?.day.orEmpty(),
        time = this?.time.orEmpty(),
        timezone = this?.timezone.orEmpty(),
        string = this?.string.orEmpty()
    )

// Producer
private fun Producer.toSearchDataProducer() =
    SearchDataProducers(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Producer>?.toSearchDataProducers(): List<SearchDataProducers> =
    this?.map { it.toSearchDataProducer() } ?: emptyList()

// Licensor
private fun Licensor.toSearchDataLicensor() =
    SearchDataLicensors(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Licensor>?.toSearchDataLicensors(): List<SearchDataLicensors> =
    this?.map { it.toSearchDataLicensor() } ?: emptyList()

// Studio
private fun Studio.toSearchDataStudio() =
    SearchDataStudios(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Studio>?.toSearchDataStudios(): List<SearchDataStudios> =
    this?.map { it.toSearchDataStudio() } ?: emptyList()

// Genre
private fun Genre.toSearchDataGenre() =
    SearchDataGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Genre>?.toSearchDataGenres(): List<SearchDataGenres> =
    this?.map { it.toSearchDataGenre() } ?: emptyList()

// Explicit Genre
private fun ExplicitGenre.toSearchDataExplicitGenre() =
    SearchDataExplicitGenres(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<ExplicitGenre>?.toSearchDataExplicitGenres(): List<SearchDataExplicitGenres> =
    this?.map { it.toSearchDataExplicitGenre() } ?: emptyList()

// Theme
private fun Theme.toSearchDataTheme() =
    SearchDataThemes(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Theme>?.toSearchDataThemes(): List<SearchDataThemes> =
    this?.map { it.toSearchDataTheme() } ?: emptyList()

// Demographic
private fun Demographic.toSearchDataDemographic() =
    SearchDataDemographics(
        id = this.malId ?: 0,
        type = this.type.orEmpty(),
        name = this.name.orEmpty(),
        url = this.url.orEmpty()
    )
fun List<Demographic>?.toSearchDataDemographics(): List<SearchDataDemographics> =
    this?.map { it.toSearchDataDemographic() } ?: emptyList()

fun Collection<SearchAnimeData>?.toSearchAnimeList() =
    this?.map {
        it.toSearchAnime()
    } ?: listOf()