package com.cococlip.android.model

import java.io.Serializable

/**
 * 位置
 *
 * @author Taro Nagasawa
 */
data public class Location(public val latitude: Double,
                           public val longitude: Double,
                           public val address: String? = null) : Serializable {

    public fun getTextForDisplay(): String = address ?: "(${latitude}, ${longitude})"
}