package com.sotwtm.support.util

import android.support.annotation.IntDef
import android.support.design.widget.Snackbar


/**
 * [Snackbar] duration constants.
 * @author sheunogn
 */
@IntDef(Snackbar.LENGTH_INDEFINITE, Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG)
@Retention(AnnotationRetention.SOURCE)
annotation class SnackbarDuration
