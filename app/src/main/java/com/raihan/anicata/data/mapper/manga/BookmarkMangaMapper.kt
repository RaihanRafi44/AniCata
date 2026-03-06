package com.raihan.anicata.data.mapper.manga

import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.data.model.storage.UserBookmarkManga

fun MangaDetailFull.toUserBookmarkManga() =
    UserBookmarkManga(
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

fun Collection<MangaDetailFull>.toBookmarkMangaList() =
    this.map {
        it.toUserBookmarkManga()
    }