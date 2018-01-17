package com.sotwtm.support.util.databinding

import android.content.res.ColorStateList
import android.databinding.BindingAdapter
import android.graphics.PorterDuff
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.widget.ProgressBar

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [ProgressBar]
 *
 * @author John
 */
object ProgressBarAppHelpfulDataBinding {

    @JvmStatic
    @BindingAdapter(value = ["app:setProgressColorRes"])
    fun setProgressBarColorRes(view: ProgressBar,
                               @ColorRes colorRes: Int) {
        setProgressBarColor(view, ContextCompat.getColor(view.context, colorRes))
    }

    @JvmStatic
    @BindingAdapter(value = ["app:setProgressColor"])
    fun setProgressBarColor(view: ProgressBar,
                            @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.progressTintList = ColorStateList.valueOf(color)
        } else {
            view.progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }
}