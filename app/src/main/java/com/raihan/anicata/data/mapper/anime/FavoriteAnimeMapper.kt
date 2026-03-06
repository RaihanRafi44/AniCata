package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.storage.UserFavoriteAnime

fun AnimeData.toUserFavoriteAnime() =
    UserFavoriteAnime(
        id = this.id.toString(),
        title = this.title,
        imageUrl = this.images.jpg.imageUrl ?: "",
        score = this.score ?: 0.0,
        members = this.members,
        type = this.type ?: "",
        episodes = this.episodes,
        airedFrom = this.aired?.from,
        airedTo = this.aired?.to,
        savedAt = System.currentTimeMillis()
    )

fun Collection<AnimeData>?.toFavoriteAnimeList() =
    this?.map {
        it.toUserFavoriteAnime()
    } ?: listOf()