package com.example.flashtextview

import android.text.Layout
import androidx.collection.LruCache

abstract class LayoutRecyclerPool<K>(size: Int) {

    data class LayoutWrapper(val width: Int, val layout: Layout)

    private val lruCache = LruCache<K, LayoutWrapper>(size)

    fun obtain(key: K, text: CharSequence, width: Int): Layout {
        val cache = lruCache[key]
        if (cache != null && width == cache.width) {
            return cache.layout
        }
        val newLayout = onCreateTextLayout(key, text, width)
        lruCache.put(key, LayoutWrapper(width, newLayout))
        return newLayout
    }

    abstract fun onCreateTextLayout(key: K, text: CharSequence, width: Int): Layout

    fun clear() {
        lruCache.evictAll()
    }

}