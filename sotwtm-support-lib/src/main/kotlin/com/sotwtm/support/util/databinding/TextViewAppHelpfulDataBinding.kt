package com.sotwtm.support.util.databinding

import android.databinding.BindingAdapter
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.TextView

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [TextView]
 *
 * @author John
 */
object TextViewAppHelpfulDataBinding {

    @JvmStatic
    @BindingAdapter("underlineText")
    fun underLineText(textView: TextView,
                      underline: Boolean) {
        if (underline) {
            textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        }
    }

    @JvmStatic
    @BindingAdapter("typeface", "textStyle",
            requireAll = false)
    fun decorateText(view: TextView,
                     typeface: Typeface?,
                     textStyle: Int?) {
        view.setTypeface(
                if (typeface == null) view.typeface else typeface,
                if (textStyle == null) 0 else textStyle)
    }
}