package com.raihan.anicata.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dropShadow(
    color: Color = Color.Black,
    alpha: Float = 0.75f,
    shadowRadius: Dp = 16.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 4.dp,
    cornerRadius: Dp = 12.dp
) = this.drawBehind {
    val shadowColor = color.copy(alpha = alpha).toArgb()
    val paint = android.graphics.Paint().apply {
        this.color = android.graphics.Color.TRANSPARENT
        setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
    }

    drawContext.canvas.nativeCanvas.apply {
        save()
        drawRoundRect(
            0f,
            0f,
            size.width,
            size.height,
            cornerRadius.toPx(),
            cornerRadius.toPx(),
            paint
        )
        restore()
    }
}

