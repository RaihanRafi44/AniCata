package com.raihan.anicata.data.model.anime.characters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterAnime( // Ubah nama dari 'Data' agar tidak bentrok
    var character: CharacterData?,
    var favorites: Int?,
    var role: String?,
    var voiceActors: List<VoiceActorCharacterAnime>
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Characters.kt)
 */
@Parcelize
data class CharacterData(
    var images: ImagesCharacterAnime?,
    var malId: Int?,
    var name: String?,
    var url: String?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya VoiceActor.kt)
 */
@Parcelize
data class VoiceActorCharacterAnime(
    var language: String?,
    var person: PersonCharacterAnime?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Person.kt)
 */
@Parcelize
data class PersonCharacterAnime(
    var images: ImagesCharacterAnimeX?,
    var malId: Int?,
    var name: String?,
    var url: String?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Images.kt)
 */
@Parcelize
data class ImagesCharacterAnime(
    var jpg: JpgCharacterAnime?,
    var webp: WebpCharacterAnime?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Webp.kt)
 */
@Parcelize
data class WebpCharacterAnime(
    var imageUrl: String?,
    var smallImageUrl: String?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya ImagesX.kt)
 */
@Parcelize
data class ImagesCharacterAnimeX(
    var jpg: JpgCharacterAnime?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Jpg.kt)
 */
@Parcelize
data class JpgCharacterAnime(
    var imageUrl: String?
) : Parcelable
