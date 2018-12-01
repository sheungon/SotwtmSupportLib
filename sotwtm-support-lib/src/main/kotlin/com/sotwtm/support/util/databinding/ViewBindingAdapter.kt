package com.sotwtm.support.util.databinding

import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.view.View
import com.sotwtm.support.util.SnackbarUtil

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("backgroundResource")
    fun setBackgroundRes(
        view: View,
        @DrawableRes resId: Int?
    ) {
        if (resId == null) {
            view.background = null
        } else {
            view.setBackgroundResource(resId)
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["snackbarMsg",
            "snackbarDuration"],
        requireAll = false
    )
    fun showSnackbar(
        view: View,
        msg: String?,
        duration: Int?
    ) {
        if (msg == null) return
        SnackbarUtil.make(view, msg, duration ?: Snackbar.LENGTH_LONG)
    }
}