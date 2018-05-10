package com.sotwtm.support.util.databinding

import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.view.View

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("backgroundResource")
    fun setBackgroundRes(view: View,
                         @DrawableRes resId: Int?) {
        if (resId == null) {
            view.background = null
        } else {
            view.setBackgroundResource(resId)
        }
    }
}