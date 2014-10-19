package com.cococlip.android.util

import android.app.Fragment
import android.view.View

/**
 * ビューをインジェクトします。
 *
 * @author Taro Nagasawa
 */
public trait ViewInjector<T> {
    fun get(thisRef: Any, prop: PropertyMetadata): T
}

public fun <T : View> Fragment.viewInjector(resId: Int): ViewInjector<T> = object : ViewInjector<T> {

    private var view: T? = null

    override fun get(thisRef: Any, prop: PropertyMetadata): T {
        if (view == null) {
            view = getView().findViewById(resId) as T
        }

        return view!!
    }
}
