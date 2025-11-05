package com.raihan.anicata.data.model.manga.staff

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Model UI Parcelable (sebelumnya Data.kt)
 * Nama diubah menjadi 'MangaStaffData' agar lebih spesifik
 */
@Parcelize
data class StaffManga(
    var person: PersonStaffManga?,
    var positions: List<String>
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Person.kt)
 */
@Parcelize
data class PersonStaffManga(
    var images: ImagesStaffManga?,
    var malId: Int?,
    var name: String?,
    var url: String?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Images.kt)
 */
@Parcelize
data class ImagesStaffManga(
    var jpg: JpgStaffManga?
) : Parcelable

/**
 * Model UI Parcelable (sebelumnya Jpg.kt)
 */
@Parcelize
data class JpgStaffManga(
    var imageUrl: String?
) : Parcelable