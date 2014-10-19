package com.cococlip.android.app

import android.app.Fragment
import android.widget.TextView
import com.cococlip.android.R
import com.cococlip.android.util.viewInjector
import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View

/**
 * メインのフラグメントです。
 *
 * @author Taro Nagasawa
 */
public class MainFragment : Fragment() {

    private val locationView: TextView by viewInjector(R.id.location_view)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    public fun setLocation(location: Location) {
        locationView.setText("${location.getLatitude()}, ${location.getLongitude()}")
    }
}