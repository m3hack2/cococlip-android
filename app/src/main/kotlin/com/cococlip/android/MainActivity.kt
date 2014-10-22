package com.cococlip.android

import android.app.Activity
import android.os.Bundle
import android.location.LocationManager
import kotlin.properties.Delegates
import com.cococlip.android.util.getLocationManager
import android.location.Criteria
import android.location.LocationListener
import android.location.Location as AndroidLocation
import com.cococlip.android.app.MainFragment
import com.cococlip.android.app.ClipPostFragment
import com.cococlip.android.util.toLocation
import com.cococlip.android.model.Location

public class MainActivity : Activity(), MainFragment.Interface {

    private val locationManager: LocationManager by Delegates.lazy {
        getLocationManager()
    }

    private val locationProvider: String
        get() = Criteria().let {
            it.setAccuracy(Criteria.ACCURACY_FINE)
            locationManager.getBestProvider(it, true)
        }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: AndroidLocation?) {
            location?.let {
                mainFragment.setLocation(it.toLocation())
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }

    private val mainFragment by Delegates.lazy {
        MainFragment()
    }

    private val clipPostFragment by Delegates.lazy {
        ClipPostFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mainFragment)
                    .commit()
        }
    }

    override fun onResume() {
        super<Activity>.onResume()
        locationManager.requestLocationUpdates(locationProvider, 0L, 0F, locationListener)
    }

    override fun onPause() {
        locationManager.removeUpdates(locationListener)
        super<Activity>.onPause()
    }

    override fun showClipPostFragment(location: Location) {
        clipPostFragment.setArgument(location)
        getFragmentManager().beginTransaction()
                .replace(R.id.container, clipPostFragment)
                .addToBackStack(null)
                .commit()
    }
}
