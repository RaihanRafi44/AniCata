package com.raihan.anicata.data.mapper.manga

import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.data.model.storage.UserFavoriteManga

fun MangaDetailFull.toUserFavoriteManga() =
    UserFavoriteManga(
        id = this.id.toString(),
        title = this.title,
        imageUrl = this.images.jpg.imageUrl,
        score = this.score ?: 0.0,
        members = this.members ?: 0,
        type = this.type,
        chapters = this.chapters,
        publishedFrom = this.published.from,
        publishedTo = this.published.to,
        savedAt = System.currentTimeMillis()
    )

fun Collection<MangaDetailFull>.toFavoriteMangaList() =
    this.map {
        it.toUserFavoriteManga()
    }