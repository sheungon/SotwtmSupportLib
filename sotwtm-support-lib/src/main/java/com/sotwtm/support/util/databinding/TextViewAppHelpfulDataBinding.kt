package com.sotwtm.support.util.databinding

import android.databinding.BindingAdapter
import android.graphics.Paint
import android.widget.TextView

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [TextView]
 *
 * @author John
 */
object TextViewAppHelpfulDataBinding {

    @JvmStatic
    @BindingAdapter("bind:underlineText")
    fun underLineText(textView: TextView,
                      underline: Boolean) {
        if (underline) {
            textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        }
    }
}