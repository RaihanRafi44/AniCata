package com.raihan.anicata.data.mapper.manga

import com.raihan.anicata.data.model.manga.staff.ImagesStaffManga
import com.raihan.anicata.data.model.manga.staff.JpgStaffManga
import com.raihan.anicata.data.model.manga.staff.PersonStaffManga
import com.raihan.anicata.data.model.manga.staff.StaffManga
import com.raihan.anicata.data.source.network.model.manga.staff.Images
import com.raihan.anicata.data.source.network.model.manga.staff.Jpg
import com.raihan.anicata.data.source.network.model.manga.staff.Person
import com.raihan.anicata.data.source.network.model.manga.staff.StaffMangaData
import kotlin.collections.orEmpty

fun StaffMangaData?.toDetailStaffManga() =
    StaffManga(
        person = this?.person.toDetailPersonManga(),
        positions = this?.positions.orEmpty()
    )

fun Person?.toDetailPersonManga() =
    PersonStaffManga(
        images = this?.images.toDetailImagesStaffManga(),
        malId = this?.malId ?: 0,
        name = this?.name.orEmpty(),
        url = this?.url
    )

fun Images?.toDetailImagesStaffManga() =
    ImagesStaffManga(
        jpg = this?.jpg.toDetailJpgStaffManga()
    )

fun Jpg?.toDetailJpgStaffManga() =
    JpgStaffManga(
        imageUrl = this?.imageUrl.orEmpty()
    )

fun Collection<StaffMangaData>?.toStaffMangaList() =
    this?.map {
        it.toDetailStaffManga()
    } ?: listOf()