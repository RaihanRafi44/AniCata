package com.raihan.anicata.data.model.manga.characters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Model UI Parcelable (sebelumnya Data.kt)
 * Nama diubah menjadi 'MangaCharacterData' agar lebih spesifik
 */
@Parcelize
data class CharacterManga(
    var character: CharacterData?,
    var favorites: Int?,
    var role: String?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Characters.kt)
 */
@Parcelize
data class CharacterData(
    var images: ImagesCharacterManga?,
    var malId: Int?,
    var name: String?,
    var url: String?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Images.kt)
 */
@Parcelize
data class ImagesCharacterManga(
    var jpg: JpgCharacterManga?,
    var webp: WebpCharacterManga?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Jpg.kt)
 */
@Parcelize
data class JpgCharacterManga(
    var imageUrl: String?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Webp.kt)
 */
@Parcelize
data class WebpCharacterManga(
    var imageUrl: String?,
    var smallImageUrl: String?
) : Parcelable
