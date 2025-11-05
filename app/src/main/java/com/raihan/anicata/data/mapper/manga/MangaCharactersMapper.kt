package com.raihan.anicata.data.mapper.manga

import com.raihan.anicata.data.model.manga.characters.CharacterData
import com.raihan.anicata.data.model.manga.characters.CharacterManga
import com.raihan.anicata.data.model.manga.characters.ImagesCharacterManga
import com.raihan.anicata.data.model.manga.characters.JpgCharacterManga
import com.raihan.anicata.data.model.manga.characters.WebpCharacterManga
import com.raihan.anicata.data.source.network.model.manga.characters.Characters
import com.raihan.anicata.data.source.network.model.manga.characters.CharactersMangaData
import com.raihan.anicata.data.source.network.model.manga.characters.Images
import com.raihan.anicata.data.source.network.model.manga.characters.Jpg
import com.raihan.anicata.data.source.network.model.manga.characters.Webp

fun CharactersMangaData?.toDetailCharacterManga() =
    CharacterManga(
        character = this?.character.toDetailCharacter(),
        favorites = this?.favorites ?: 0,
        role = this?.role.orEmpty()
    )

fun Characters?.toDetailCharacter() =
    CharacterData(
        images = this?.images.toDetailImages(),
        malId = this?.malId ?: 0,
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty()
    )

fun Images?.toDetailImages() =
    ImagesCharacterManga(
        jpg = this?.jpg.toDetailJpg(),
        webp = this?.webp.toDetailWebp()
    )

fun Webp?.toDetailWebp() =
    WebpCharacterManga(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty()
    )

fun Jpg?.toDetailJpg() =
    JpgCharacterManga(
        imageUrl = this?.imageUrl.orEmpty()
    )

fun Collection<CharactersMangaData>?.toDetailCharactersManga() =
    this?.map {
        it.toDetailCharacterManga()
    } ?: listOf()