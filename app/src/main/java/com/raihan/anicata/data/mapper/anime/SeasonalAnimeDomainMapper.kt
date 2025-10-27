package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.season.upcoming.SeasonAnimeUpcoming
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import com.raihan.anicata.data.model.domain.AiredInfo
import com.raihan.anicata.data.model.domain.DateInfo
import com.raihan.anicata.data.model.domain.PropInfo
import com.raihan.anicata.data.model.domain.SeasonalAnime

/*
// Mapper dari SeasonAnimeYear (API /season/{year}/{season}) ke Domain
fun SeasonAnimeYear.toDomain(): SeasonalAnime {
    return SeasonalAnime(
        id = this.id,
        title = this.title,
        imageUrl = this.images.jpg.imageUrl.toString(),
        aired = this.aired?.let {
            AiredInfo(
                prop = it.prop?.let { prop ->
                    PropInfo(
                        from = prop.from?.let { from -> DateInfo(from.day, from.month, from.year) },
                        to = prop.to?.let { to -> DateInfo(to.day, to.month, to.year) },
                        string = prop.string.toString()
                    )
                }
            )
        },
        members = this.members,
        episodes = if (this.episodes == 0) null else this.episodes, // Handle 0 sebagai null jika perlu
        score = this.score,
        type = this.type
    )
}

// Mapper dari SeasonAnimeUpcoming (API /seasons/upcoming) ke Domain
fun SeasonAnimeUpcoming.toDomain(): SeasonalAnime {
    return SeasonalAnime(
        id = this.id,
        title = this.title,
        imageUrl = this.images.jpg.imageUrl.toString(),
        aired = this.aired.let {
            AiredInfo(
                prop = it?.prop.let { prop ->
                    PropInfo(
                        from = prop?.from.let { from -> DateInfo(from?.day, from?.month, from?.year) },
                        to = prop?.to.let { to -> DateInfo(to?.day, to?.month, to?.year) },
                        string = prop?.string.toString()
                    )
                }
            )
        },
        members = this.members,
        episodes = if (this.episodes == 0) null else this.episodes, // Handle 0 sebagai null jika perlu
        score = this.score,
        type = this.type
    )
}*/

// Mapper dari SeasonAnimeYear (API /season/{year}/{season}) ke Domain
fun SeasonAnimeYear.toDomain(): SeasonalAnime {
    return SeasonalAnime(
        id = this.id,
        title = this.title,
        // Gunakan nullable '?' karena ImageFormatJpg memiliki imageUrl nullable
        imageUrl = this.images.jpg.imageUrl ?: "",
        aired = this.aired?.let {
            AiredInfo(
                prop = it.prop?.let { prop ->
                    PropInfo(
                        from = prop.from?.let { from -> DateInfo(from.day, from.month, from.year) },
                        to = prop.to?.let { to -> DateInfo(to.day, to.month, to.year) },
                        string = prop.string ?: "" // Handle nullable string
                    )
                }
            )
        },
        members = this.members,
        // Mapper Anda mengubah null 'episodes' menjadi 0, kita ubah kembali ke null jika 0
        episodes = if (this.episodes == 0) null else this.episodes,
        score = this.score,
        type = this.type
    )
}

// Mapper dari SeasonAnimeUpcoming (API /seasons/upcoming) ke Domain
fun SeasonAnimeUpcoming.toDomain(): SeasonalAnime {
    return SeasonalAnime(
        id = this.id,
        title = this.title,
        imageUrl = this.images.jpg.imageUrl ?: "",
        aired = this.aired?.let { // 'aired' di upcoming tidak nullable, tapi kita cek untuk keamanan
            AiredInfo(
                prop = it.prop?.let { prop ->
                    PropInfo(
                        from = prop.from?.let { from -> DateInfo(from.day, from.month, from.year) },
                        to = prop.to?.let { to -> DateInfo(to.day, to.month, to.year) },
                        string = prop.string ?: ""
                    )
                }
            )
        },
        members = this.members,
        episodes = if (this.episodes == 0) null else this.episodes,
        score = this.score,
        type = this.type
    )
}
