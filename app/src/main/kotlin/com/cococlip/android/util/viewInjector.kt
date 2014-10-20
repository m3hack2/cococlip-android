package com.cococlip.android.util

import android.app.Fragment
import android.view.View
import android.view.ViewGroup

/**
 * ビューをインジェクトします。
 *
 * @author Taro Nagasawa
 */
public trait ViewInjector<T : View> {
    fun get(thisRef: Any, prop: PropertyMetadata): T
}

private abstract class AbstractViewInjector<T : View>(private val resId: Int) : ViewInjector<T> {
    private var view: T? = null

    override fun get(thisRef: Any, prop: PropertyMetadata): T {
        if (view == null) {
            view = findViewById_(resId)
        }

        return view!!
    }

    abstract fun findViewById_(resId: Int): T
}

public fun <T : View> Fragment.viewInjector(resId: Int): ViewInjector<T> = object : AbstractViewInjector<T>(resId) {
    override fun findViewById_(resId: Int): T = getView().findViewById(resId) as T
}

public fun <T : View> ViewGroup.viewInjector(resId: Int): ViewInjector<T> = object : AbstractViewInjector<T>(resId) {
    override fun findViewById_(resId: Int): T = findViewById(resId) as T
}