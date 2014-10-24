package com.cococlip.android

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import butterknife.bindView
import android.widget.TextView
import android.content.Context
import android.content.Intent
import com.cococlip.android.util.startActivity
import kotlin.properties.Delegates
import com.cococlip.android.rx.Rx
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers
import android.widget.Toast
import com.squareup.picasso.Picasso
import android.view.MenuItem

public class ClipActivity : Activity() {

    public class object {
        private val EXTRA_ID = "EXTRA_ID"

        public fun launch(context: Context, id: String) {
            Intent(context, javaClass<ClipActivity>())
                    .putExtra(EXTRA_ID, id)
                    .startActivity(context)
        }
    }

    private val id: String by Delegates.lazy {
        getIntent().getStringExtra(EXTRA_ID)
    }

    private val imageView: ImageView by bindView(R.id.image_view)

    private val bodyView: TextView by bindView(R.id.body_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getActionBar().setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.activity_clip)
        initViews()

        load()
    }

    private fun initViews() {
        imageView
        bodyView
    }

    private fun load() {
        Rx.clip(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    null
                }
                .subscribe {
                    if (it == null) {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show()
                    } else {
                        setTitle(it.title)
                        bodyView.setText(it.body)
                        Picasso.with(getApplicationContext()).load(it.image1Url).into(imageView)
                    }
                }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> finish()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}