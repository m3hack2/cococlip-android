package com.cococlip.android

import android.app.Activity
import android.os.Bundle
import com.cococlip.android.model.Location
import android.content.Intent
import android.content.Context
import com.cococlip.android.util.startActivity
import kotlin.properties.Delegates
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import butterknife.bindView

/**
 * クリップ投稿のためのアクティビティです。
 *
 * @author Taro Nagasawa
 */
public class ClipPostActivity : Activity() {

    public class object {
        private val EXTRA_LOCATION = "EXTRA_LOCATION"

        public fun launch(context: Context, location: Location) {
            Intent(context, javaClass<ClipPostActivity>())
                    .putExtra(EXTRA_LOCATION, location)
                    .startActivity(context)
        }
    }

    private val location: Location by Delegates.lazy {
        android.util.Log.d("TEST", "OK")
        getIntent().getSerializableExtra(EXTRA_LOCATION) as Location
    }

    private val addressView: TextView by bindView(R.id.address_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getActionBar().setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.fragment_clip_post)
        addressView.setText(location.getTextForDisplay())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.clip_post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> finish()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}