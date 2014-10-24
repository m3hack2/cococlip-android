package com.cococlip.android.app

import android.content.Context
import android.widget.FrameLayout
import android.view.View
import com.cococlip.android.R
import android.widget.TextView
import com.cococlip.android.model.Clip
import butterknife.bindView
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * @author Taro Nagasawa
 */
public class ClipListItemView(context: Context) : FrameLayout(context) {

    private val thumbnailView: ImageView by bindView(R.id.thumbnail_view)

    private val titleView: TextView by bindView(R.id.title_view)

    private val addressView: TextView by bindView(R.id.address_view)

    private var alreadyInflated: Boolean = false

    override fun onFinishInflate() {
        if (!alreadyInflated) {
            alreadyInflated = true
            View.inflate(getContext(), R.layout.view_clip_list_item, this)
        }
        super.onFinishInflate()
    }

    public fun bind(clip: Clip) {
        titleView.setText(clip.title)
        addressView.setText(clip.location.getTextForDisplay())

        clip.thumbnail1Url?.let {
            Picasso.with(getContext()).load(it).into(thumbnailView)
        }
    }

    public class object {
        public fun build(context: Context): ClipListItemView = ClipListItemView(context).let {
            it.onFinishInflate()
            it
        }
    }
}