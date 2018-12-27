package com.sotwtm.support.util.databinding

import android.content.Context
import android.databinding.BindingConversion
import android.support.annotation.BoolRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import com.sotwtm.util.Log

/**
 * DataBinding BindingAdapter and BindingConversion created for easier implementation for Android DataBinding.
 *
 * @author sheunogn
 */
object AppHelpfulDataBindingUtil {

    @JvmStatic
    @BindingConversion
    fun stringToDouble(charSequence: CharSequence?): Double {

        try {
            if (charSequence?.isEmpty() != false) {
                return 0.0
            }
            return java.lang.Double.valueOf(charSequence.toString())
        } catch (e: Exception) {
            Log.e("Error on convert charSequence to int : $charSequence", e)
        }

        return 0.0
    }

    @JvmStatic
    fun getBoolean(context: Context,
                   @BoolRes boolRes: Int): Boolean = context.resources.getBoolean(boolRes)

    @JvmStatic
    @ColorInt
    fun getColor(context: Context,
                 @ColorRes colorRes: Int): Int = ContextCompat.getColor(context, colorRes)

    @JvmStatic
    fun getString(context: Context,
                  @StringRes stringRes: Int): String = context.getString(stringRes)
}
