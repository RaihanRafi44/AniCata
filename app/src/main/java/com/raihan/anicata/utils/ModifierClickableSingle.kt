package com.raihan.anicata.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

// Modifier kustom untuk mencegah klik beruntun (Double Click / Ghost Click)
fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    this.clickable(
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        indication = androidx.compose.foundation.LocalIndication.current
    ) {
        val currentTime = System.currentTimeMillis()
        // Beri jeda 800ms agar tidak terjadi klik tembus ke layar berikutnya
        if (currentTime - lastClickTime > 800L) {
            lastClickTime = currentTime
            onClick()
        }
    }
}