package com.cococlip.android

import android.app.Activity
import android.os.Bundle
import android.location.LocationManager
import kotlin.properties.Delegates
import com.cococlip.android.util.getLocationManager
import android.location.Criteria
import android.location.LocationListener
import android.location.Location as AndroidLocation
import android.widget.TextView
import android.widget.ListView
import butterknife.bindView
import com.cococlip.android.model.Location
import com.cococlip.android.rx.Rx
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers
import com.cococlip.android.app.ClipListAdapter
import com.cococlip.android.util.toLocation
import android.view.Menu
import android.view.MenuItem
import java.util.concurrent.TimeUnit

public class MainActivity : Activity() {

    private val locationView: TextView by bindView(R.id.location_view)

    private val listView: ListView by bindView(R.id.list_view)

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
                setLocation(it.toLocation())
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)
        initViews()
    }

    private fun initViews() {
        locationView
        listView
    }

    override fun onResume() {
        super<Activity>.onResume()
        locationManager.requestLocationUpdates(locationProvider, TimeUnit.SECONDS.toMillis(30), 1.0F, locationListener)
    }

    override fun onPause() {
        locationManager.removeUpdates(locationListener)
        super<Activity>.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        locationManager.getLastKnownLocation(locationProvider)?.let {
            if (item?.getItemId() == R.id.action_post_clip) {
                ClipPostActivity.launch(this@MainActivity, it.toLocation())
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setLocation(location: Location) {
        locationView.setText(location.getTextForDisplay())
        Rx.clips(location)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    listOf()
                }
                .subscribe {
                    val adapter = ClipListAdapter(getApplicationContext(), 0, it)
                    listView.setAdapter(adapter)
                }
    }
}
