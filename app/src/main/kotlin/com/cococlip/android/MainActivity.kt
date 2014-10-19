package com.cococlip.android

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.location.LocationManager
import kotlin.properties.Delegates
import com.cococlip.android.util.getLocationManager
import android.location.Criteria
import android.location.LocationListener
import android.location.Location
import com.cococlip.android.app.MainFragment

public class MainActivity : Activity() {

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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mainFragment)
                    .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        locationManager.requestLocationUpdates(locationProvider, 0L, 0F, locationListener)
    }

    override fun onPause() {
        locationManager.removeUpdates(locationListener)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.getItemId() == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
