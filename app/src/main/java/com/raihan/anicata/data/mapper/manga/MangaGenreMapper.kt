package com.raihan.anicata.data.mapper.manga

import com.raihan.anicata.data.model.manga.genres.GenreManga
import com.raihan.anicata.data.source.network.model.manga.genres.GenreMangaData

fun GenreMangaData?.toGenreManga() =
    GenreManga(
        id = this?.malId ?: 0,
        name = this?.name.orEmpty(),
        count = this?.count ?: 0,
        url = this?.url.orEmpty()
    )

fun Collection<GenreMangaData>?.toGenreMangaList() =
    this?.map {
        it.toGenreManga()
    } ?: listOf()