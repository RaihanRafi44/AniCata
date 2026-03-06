package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.storage.UserBookmarkAnime

fun AnimeData.toUserSavedAnime() =
    UserBookmarkAnime(
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

fun Collection<AnimeData>?.toSavedAnimeList() =
    this?.map {
        it.toUserSavedAnime()
    } ?: listOf()