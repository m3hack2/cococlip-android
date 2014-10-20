package com.cococlip.android.app

import android.content.Context
import android.widget.FrameLayout
import android.view.View
import com.cococlip.android.R
import android.widget.TextView
import com.cococlip.android.model.Clip
import com.cococlip.android.util.viewInjector

/**
 * @author Taro Nagasawa
 */
public class ClipListItemView(context: Context) : FrameLayout(context) {

    private val titleView: TextView by viewInjector(R.id.title_view)

    private val addressView: TextView by viewInjector(R.id.address_view)

    private val bodyView: TextView by viewInjector(R.id.body_view)

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
    }

    public class object {
        public fun build(context: Context): ClipListItemView = ClipListItemView(context).let {
            it.onFinishInflate()
            it
        }
    }
}