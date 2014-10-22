package com.cococlip.android.app

import android.app.Fragment
import android.widget.TextView
import com.cococlip.android.R
import com.cococlip.android.util.viewInjector
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.cococlip.android.rx.Rx
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlin.properties.Delegates
import android.app.Activity
import com.cococlip.android.model.Location

/**
 * メインのフラグメントです。
 *
 * @author Taro Nagasawa
 */
public class MainFragment : Fragment() {

    public trait Interface {
        fun showClipPostFragment(location: Location)
    }

    private val locationView: TextView by viewInjector(R.id.location_view)

    private val listView: ListView by viewInjector(R.id.list_view)

    private var interface: Interface by Delegates.notNull()

    private var clipPostMenuItem: MenuItem by Delegates.notNull()

    private var location: Location? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)

        if (activity is Interface) {
            interface = activity
        } else {
            throw IllegalArgumentException("activity must implements ${javaClass<Interface>().getCanonicalName()}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Fragment>.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        clipPostMenuItem = menu.findItem(R.id.action_post_clip)
    }

    override fun onStart() {
        super.onStart()
        getActivity()?.let {
            it.setTitle(R.string.app_name)
            it.getActionBar().setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // TODO
        if (item.getItemId() == R.id.action_post_clip && location != null) {
            interface.showClipPostFragment(location!!) // ここでは絶対NotNullなのに。。
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    public fun setLocation(location: Location) {
        this.location = location
        locationView.setText(location.getTextForDisplay())
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        // TODO
        Rx.clips(22.0, 22.0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    listOf()
                }
                .subscribe {
                    val adapter = ClipListAdapter(getActivity().getApplicationContext(), 0, it)
                    listView.setAdapter(adapter)
                }
    }
}