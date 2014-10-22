package com.cococlip.android.model

/**
 * クリップです
 *
 * @author Taro Nagasawa
 */
data class Clip(public val id: String,
                public val title: String,
                public val location: Location,
                public val image1Url: String? = null,
                public val image2Url: String? = null,
                public val thumbnail1Url: String? = null,
                public val thumbnail2Utl: String? = null)