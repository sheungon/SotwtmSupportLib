package com.sotwtm.support.util

import android.content.Context
import android.support.annotation.IntDef
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.sotwtm.support.R


/**
 * Util for [Snackbar] creation
 * @author John
 */

object SnackbarUtil {

    @IntDef(Snackbar.LENGTH_INDEFINITE, Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG)
    @Retention(AnnotationRetention.SOURCE)
    annotation class SnackbarDuration

    fun create(context: Context,
               rootView: View,
               message: String,
               @SnackbarDuration duration: Int): Snackbar {

        val snackbar = Snackbar.make(rootView, message, duration)
                .setActionTextColor(ContextCompat.getColor(context, R.color.snackbar_action_text))

        val view = snackbar.view

        val snackbarText = view.findViewById<TextView?>(android.support.design.R.id.snackbar_text)
        snackbarText?.setTextColor(ContextCompat.getColor(context, R.color.snackbar_text))

        view.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbar_bg))

        return snackbar
    }
}
