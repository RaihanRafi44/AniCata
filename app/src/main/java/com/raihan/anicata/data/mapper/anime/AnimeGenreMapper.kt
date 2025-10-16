package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.genres.GenreAnime
import com.raihan.anicata.data.source.network.model.anime.genres.GenreAnimeData

fun GenreAnimeData?.toGenreAnime() =
    GenreAnime(
        id = this?.malId ?: 0,
        name = this?.name.orEmpty(),
        count = this?.count ?: 0,
        url = this?.url.orEmpty()
    )

fun Collection<GenreAnimeData>?.toGenreAnimeList() =
    this?.map {
        it.toGenreAnime()
    } ?: listOf()