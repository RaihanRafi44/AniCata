package com.raihan.anicata.data.mapper.anime

import com.raihan.anicata.data.model.anime.staff.ImagesStaffAnime
import com.raihan.anicata.data.model.anime.staff.JpgStaffAnime
import com.raihan.anicata.data.model.anime.staff.PersonStaffAnime
import com.raihan.anicata.data.model.anime.staff.StaffAnime
import com.raihan.anicata.data.source.network.model.anime.staff.Images
import com.raihan.anicata.data.source.network.model.anime.staff.Jpg
import com.raihan.anicata.data.source.network.model.anime.staff.Person
import com.raihan.anicata.data.source.network.model.anime.staff.StaffAnimeData

fun StaffAnimeData?.toDetailStaffAnime() =
    StaffAnime(
        person = this?.person.toDetailPersonAnime(),
        positions = this?.positions.orEmpty()
    )

fun Person?.toDetailPersonAnime() =
    PersonStaffAnime(
        images = this?.images.toDetailImagesStaffAnime(),
        malId = this?.malId ?: 0,
        name = this?.name.orEmpty(),
        url = this?.url
    )

fun Images?.toDetailImagesStaffAnime() =
    ImagesStaffAnime(
        jpg = this?.jpg.toDetailJpgStaffAnime()
    )

fun Jpg?.toDetailJpgStaffAnime() =
    JpgStaffAnime(
        imageUrl = this?.imageUrl.orEmpty()
    )

fun Collection<StaffAnimeData>?.toStaffAnimeList() =
    this?.map {
        it.toDetailStaffAnime()
    } ?: listOf()
