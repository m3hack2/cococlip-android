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
import com.cococlip.android.rx.Rx
import android.widget.EditText
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import android.widget.Toast

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
        getIntent().getSerializableExtra(EXTRA_LOCATION) as Location
    }

    private val titleEditText: EditText by bindView(R.id.title_edit_text)

    private val bodyEditText: EditText by bindView(R.id.body_edit_text)

    private val addressView: TextView by bindView(R.id.address_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getActionBar().setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.fragment_clip_post)
        initViews()
    }

    private fun initViews() {
        titleEditText
        bodyEditText
        addressView.setText(location.getTextForDisplay())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.clip_post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> finish()
            R.id.action_send -> send()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun send() {
        // TODO くるくる出す

        val title = titleEditText.getText().toString()
        val body = bodyEditText.getText().toString()
        Rx.post(title, body, location)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    null
                }
                .subscribe {
                    // TODO くるくる消す
                    if (it != null) {
                        finish()
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.msg_error_post, Toast.LENGTH_SHORT).show()
                    }
                }
    }
}