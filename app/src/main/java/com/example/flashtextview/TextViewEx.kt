package com.example.flashtextview

import android.text.Layout
import kotlin.math.ceil

fun desired(layout: Layout?): Int {
    if (layout == null) {
        return 0
    }
    val n = layout.lineCount
    val text = layout.text
    var max = 0F

    for (i in 0 until n - 1) {
        if (text[layout.getLineEnd(i) - 1] != '\n') {
            return -1
        }
    }

    for (i in 0 until n) {
        max = max.coerceAtLeast(layout.getLineWidth(i))
    }

    return ceil(max.toDouble()).toInt()
}