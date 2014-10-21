package com.cococlip.android

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import android.location.LocationManager
import kotlin.properties.Delegates
import com.cococlip.android.util.getLocationManager
import android.location.Criteria
import android.location.LocationListener
import android.location.Location
import com.cococlip.android.app.MainFragment

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
        override fun onLocationChanged(location: Location?) {
            location?.let {
                mainFragment.setLocation(it)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }

    private val mainFragment: MainFragment by Delegates.lazy {
        MainFragment()
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

    override fun showClipPostFragment() {
        // TODO
        Toast.makeText(this, "showClipPostFragment", Toast.LENGTH_SHORT).show()
    }
}
