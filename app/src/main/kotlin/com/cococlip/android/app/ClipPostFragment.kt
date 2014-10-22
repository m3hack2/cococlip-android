package com.cococlip.android.app

import android.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.cococlip.android.R
import android.view.MenuItem
import android.view.Menu
import android.view.MenuInflater
import com.cococlip.android.model.Location
import kotlin.properties.Delegates
import android.widget.TextView
import com.cococlip.android.util.viewInjector

/**
 * クリップ投稿のためのフラグメントです。
 *
 * @author Taro Nagasawa
 */
public class ClipPostFragment : Fragment() {

    public class object {
        private val EXTRA_LOCATION = "EXTRA_LOCATION"
    }

    private var location: Location by Delegates.notNull()

    private val addressView: TextView by viewInjector(R.id.address_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        getArguments().let {
            location = it.getSerializable(EXTRA_LOCATION) as Location
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_clip_post, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        addressView.setText(location.getTextForDisplay())
    }

    override fun onStart() {
        super.onStart()
        getActivity()?.let {
            it.setTitle(R.string.label_post)
            it.getActionBar().setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.clip_post, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> getFragmentManager().popBackStack()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    fun setArgument(location: Location) {
        Bundle().let {
            it.putSerializable(EXTRA_LOCATION, location)
            setArguments(it)
        }
    }
}