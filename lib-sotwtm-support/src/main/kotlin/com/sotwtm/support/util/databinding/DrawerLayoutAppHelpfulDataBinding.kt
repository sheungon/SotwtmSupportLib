package com.sotwtm.support.util.databinding

import android.databinding.BindingAdapter
import android.support.v4.widget.DrawerLayout

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [DrawerLayout]
 *
 * @author sheunogn
 */
object DrawerLayoutAppHelpfulDataBinding {

    @JvmStatic
    @BindingAdapter("addDrawerListener")
    fun addDrawerListener(drawerLayout: DrawerLayout,
                          listener: DrawerLayout.DrawerListener?) {
        if (listener != null) {
            drawerLayout.addDrawerListener(listener)
        }
    }
}