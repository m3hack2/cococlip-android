package com.cococlip.android.util

import android.content.Context
import android.location.LocationManager
import android.app.Fragment

/**
 * @author Taro Nagasawa
 */

public fun Context.getLocationManager(): LocationManager
        = getSystemService(Context.LOCATION_SERVICE) as LocationManager
