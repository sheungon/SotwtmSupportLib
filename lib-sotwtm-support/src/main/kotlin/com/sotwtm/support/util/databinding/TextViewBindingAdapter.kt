package com.sotwtm.support.util.databinding

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.widget.TextView

/**
 * @author sheunogn
 */

object TextViewBindingAdapter {

    @JvmStatic
    @Synchronized
    @BindingAdapter(
        value = ["showError"],
        requireAll = false
    )
    fun showError(
        view: TextView,
        error: String?
    ) {

        view.error = error
        if (error != null) {
            view.requestFocus()
        }
    }

    @JvmStatic
    @Synchronized
    @BindingAdapter(
        value = ["showErrorRes"],
        requireAll = false
    )
    fun showError(
        view: TextView,
        errorRes: Int?
    ) {
        showError(view, if (errorRes == null) null else view.context.getString(errorRes))
    }

    @JvmStatic
    @BindingAdapter("textColorRes")
    fun setTextColorRes(
        view: TextView,
        textColorRes: Int
    ) {
        view.setTextColor(ContextCompat.getColor(view.context, textColorRes))
    }

    @JvmStatic
    @BindingAdapter(
        value = ["drawableResLeft",
            "drawableResRight",
            "drawableResTop",
            "drawableResBottom",
            "drawableResStart",
            "drawableResEnd"],
        requireAll = false
    )
    fun setDrawablesRes(
        view: TextView,
        left: Int?,
        right: Int?,
        top: Int?,
        bottom: Int?,
        start: Int?,
        end: Int?
    ) {

        val context = view.context
        val topDrawable = safeGetDrawable(context, top)
        val bottomDrawable = safeGetDrawable(context, bottom)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val startDrawable = safeGetDrawable(context, start)
            val endDrawable = safeGetDrawable(context, end)
            view.setCompoundDrawablesRelative(
                startDrawable,
                topDrawable,
                endDrawable,
                bottomDrawable
            )
            if ((start == null && left != null) ||
                (end == null && right != null)
            ) {
                val leftDrawable = if (start == null) safeGetDrawable(context, left) else startDrawable
                val rightDrawable = if (end == null) safeGetDrawable(context, right) else endDrawable
                view.setCompoundDrawables(
                    leftDrawable,
                    topDrawable,
                    rightDrawable,
                    bottomDrawable
                )
            }
        } else {
            val leftDrawable = safeGetDrawable(context, left)
            val rightDrawable = safeGetDrawable(context, right)
            view.setCompoundDrawables(
                leftDrawable,
                topDrawable,
                rightDrawable,
                bottomDrawable
            )
        }
    }

    @JvmStatic
    private fun safeGetDrawable(context: Context, resId: Int?): Drawable? =
        if (resId == null || resId == 0) null else ContextCompat.getDrawable(context, resId)
}
