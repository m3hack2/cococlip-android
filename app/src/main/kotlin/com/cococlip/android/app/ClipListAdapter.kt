package com.cococlip.android.app

import android.widget.ArrayAdapter
import com.cococlip.android.model.Clip
import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * @author Taro Nagasawa
 */
public class ClipListAdapter(
        context: Context,
        textViewResourceId: Int,
        private val clips: List<Clip>
) : ArrayAdapter<Clip>(
        context,
        textViewResourceId,
        clips
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val clip = clips[position]
        val view = (convertView ?: ClipListItemView.build(getContext())) as ClipListItemView
        view.bind(clip)
        return view
    }
}