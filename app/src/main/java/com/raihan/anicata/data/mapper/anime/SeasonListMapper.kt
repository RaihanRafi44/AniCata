package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.season.list.SeasonAnimeList
import com.raihan.anicata.data.source.network.model.anime.seasons.lists.SeasonListsData

fun SeasonListsData?.toSeasonList() =
    SeasonAnimeList(
        year = this?.year ?: 0,
        seasons = this?.seasons.orEmpty()
    )

fun Collection<SeasonListsData>?.toSeasonAnimeList() =
    this?.map {
        it.toSeasonList()
    } ?: listOf()