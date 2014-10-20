package com.cococlip.android.model

import android.graphics.Bitmap

/**
 * クリップです
 *
 * @author Taro Nagasawa
 */
data class Clip(
        public val title: String,
        public val body: String,
        public val latitude: Float,
        public val longitude: Float,
        public val image: Bitmap? = null
)