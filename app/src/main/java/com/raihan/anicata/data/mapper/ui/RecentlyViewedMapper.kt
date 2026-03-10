package com.raihan.anicata.data.mapper.ui

import com.raihan.anicata.data.model.storage.RecentlyViewed
import com.raihan.anicata.data.source.local.database.entity.RecentlyViewedEntity

fun RecentlyViewed?.toRecentlyViewedEntity() =
    RecentlyViewedEntity(
        id = this?.id,
        title = this?.title,
        imageUrl = this?.imageUrl,
        type = this?.type,
        timestamp = this?.timestamp
    )

fun RecentlyViewedEntity?.toRecentlyViewed() =
    RecentlyViewed(
        id = this?.id,
        title = this?.title,
        imageUrl = this?.imageUrl,
        type = this?.type,
        timestamp = this?.timestamp
    )

fun List<RecentlyViewedEntity>.toListRecentlyViewed() = this.map { it.toRecentlyViewed() }