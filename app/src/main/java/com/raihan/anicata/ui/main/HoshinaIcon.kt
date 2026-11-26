package com.raihan.anicata.ui.main

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val HoshinaIcon: ImageVector
    get() {
        if (_hoshinaIcon != null) return _hoshinaIcon!!
        _hoshinaIcon = ImageVector.Builder(
            name = "HoshinaIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            val ink = SolidColor(Color.Black)

            // 1. Siluet Rambut Bob (Outline luar)
            path(
                stroke = ink,
                strokeLineWidth = 1.6f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(6.2f, 18.6f)
                curveTo(4.0f, 17.8f, 3.2f, 15.2f, 3.6f, 11.8f)
                curveTo(4.1f, 6.2f, 7.2f, 2.5f, 12f, 2.5f)
                curveTo(16.8f, 2.5f, 19.9f, 6.2f, 20.4f, 11.8f)
                curveTo(20.8f, 15.2f, 20.0f, 17.8f, 17.8f, 18.6f)
            }

            // 2. Poni Anime & Rambut Samping
            path(
                stroke = ink,
                strokeLineWidth = 1.4f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(6.4f, 12.2f)
                lineTo(8.0f, 9.2f)
                lineTo(9.4f, 11.0f)
                lineTo(11.2f, 8.2f)
                lineTo(12.6f, 10.8f)
                lineTo(14.4f, 8.6f)
                lineTo(15.8f, 11.0f)
                lineTo(17.6f, 12.2f)
            }

            // 3. Kontur Wajah / Dagu (Kosong tanpa mata & mulut)
            path(
                stroke = ink,
                strokeLineWidth = 1.4f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(6.8f, 11.8f)
                curveTo(6.8f, 15.0f, 9.0f, 17.5f, 12f, 18.2f)
                curveTo(15.0f, 17.5f, 17.2f, 15.0f, 17.2f, 11.8f)
            }

            // 4. Leher
            path(
                stroke = ink,
                strokeLineWidth = 1.3f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(10.2f, 18.0f)
                lineTo(10.2f, 21.2f)
                moveTo(13.8f, 18.0f)
                lineTo(13.8f, 21.2f)
            }

            // 5. Choker
            path(fill = ink) {
                moveTo(9.8f, 19.4f)
                lineTo(14.2f, 19.4f)
                lineTo(14.2f, 20.8f)
                lineTo(9.8f, 20.8f)
                close()
            }
        }.build()
        return _hoshinaIcon!!
    }

private var _hoshinaIcon: ImageVector? = null