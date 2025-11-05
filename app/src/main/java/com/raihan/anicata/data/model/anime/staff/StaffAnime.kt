package com.raihan.anicata.data.model.anime.staff

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Model UI Parcelable (sebelumnya Data.kt)
 * Nama diubah menjadi 'StaffData' agar lebih spesifik
 */
@Parcelize
data class StaffAnime(
    var person: PersonStaffAnime?,
    var positions: List<String>?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Person.kt)
 */
@Parcelize
data class PersonStaffAnime(
    var images: ImagesStaffAnime?,
    var malId: Int?,
    var name: String?,
    var url: String?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Images.kt)
 */
@Parcelize
data class ImagesStaffAnime(
    var jpg: JpgStaffAnime?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Jpg.kt)
 */
@Parcelize
data class JpgStaffAnime(
    var imageUrl: String?
) : Parcelable
