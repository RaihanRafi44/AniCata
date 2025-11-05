package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.characters.CharacterAnime
import com.raihan.anicata.data.model.anime.characters.CharacterData
import com.raihan.anicata.data.model.anime.characters.ImagesCharacterAnime
import com.raihan.anicata.data.model.anime.characters.ImagesCharacterAnimeX
import com.raihan.anicata.data.model.anime.characters.JpgCharacterAnime
import com.raihan.anicata.data.model.anime.characters.PersonCharacterAnime
import com.raihan.anicata.data.model.anime.characters.VoiceActorCharacterAnime
import com.raihan.anicata.data.model.anime.characters.WebpCharacterAnime
import com.raihan.anicata.data.source.network.model.anime.characters.CharacterAnimeData
import com.raihan.anicata.data.source.network.model.anime.characters.Characters
import com.raihan.anicata.data.source.network.model.anime.characters.Images
import com.raihan.anicata.data.source.network.model.anime.characters.ImagesX
import com.raihan.anicata.data.source.network.model.anime.characters.Jpg
import com.raihan.anicata.data.source.network.model.anime.characters.Person
import com.raihan.anicata.data.source.network.model.anime.characters.VoiceActor
import com.raihan.anicata.data.source.network.model.anime.characters.Webp

fun CharacterAnimeData?.toDetailCharacterAnime() =
    CharacterAnime(
        character = this?.character.toDetailCharacter(),
        favorites = this?.favorites ?: 0,
        role = this?.role.orEmpty(),
        voiceActors = this?.voiceActors.toDetailVoiceActors()
    )

fun Characters?.toDetailCharacter() =
    CharacterData(
        images = this?.images.toDetailImages(),
        malId = this?.malId ?: 0,
        name = this?.name.orEmpty(),
        url = this?.url.orEmpty()
    )

private fun VoiceActor.toDetailVoiceActor() =
    VoiceActorCharacterAnime(
        language = this.language.orEmpty(),
        person = this.person.toDetailPersonAnime()
    )

fun List<VoiceActor>?.toDetailVoiceActors(): List<VoiceActorCharacterAnime> =
    this?.map { it.toDetailVoiceActor() } ?: emptyList()

fun Person?.toDetailPersonAnime() =
    PersonCharacterAnime(
        images = this?.images.toDetailImagesX(),
        malId = this?.malId ?: 0,
        name = this?.name.orEmpty(),
        url = this?.url
    )

fun Images?.toDetailImages() =
    ImagesCharacterAnime(
        jpg = this?.jpg.toDetailJpg(),
        webp = this?.webp.toDetailWebp()
    )

fun Webp?.toDetailWebp() =
    WebpCharacterAnime(
        imageUrl = this?.imageUrl.orEmpty(),
        smallImageUrl = this?.smallImageUrl.orEmpty()
    )

fun ImagesX?.toDetailImagesX() =
    ImagesCharacterAnimeX(
        jpg = this?.jpg.toDetailJpg()
    )


fun Jpg?.toDetailJpg() =
    JpgCharacterAnime(
        imageUrl = this?.imageUrl.orEmpty()
    )

fun Collection<CharacterAnimeData>?.toDetailCharactersAnime() =
    this?.map {
        it.toDetailCharacterAnime()
    } ?: listOf()