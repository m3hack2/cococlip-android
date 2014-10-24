package com.cococlip.android.util

import android.location.Location as AndroidLocation
import com.cococlip.android.model.Location

/**
 * @author Taro Nagasawa
 */

public fun AndroidLocation.toLocation(): Location = Location(getLatitude(), getLongitude())

