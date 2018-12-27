package com.sotwtm.support.base

import android.support.annotation.StringRes
import com.sotwtm.support.R
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.util.SnackbarDuration

/**
 * @author sheunogn
 */
abstract class BaseMessenger {

    abstract val activity: AppHelpfulActivity?

    /**
     * @param msgRes The message on loading dialog
     * *
     */
    abstract fun showLoadingDialog(@StringRes msgRes: Int? = R.string.loading)

    abstract fun dismissLoadingDialog()
    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    abstract fun showSnackBar(@StringRes messageRes: Int,
                              @SnackbarDuration duration: Int)

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    abstract fun showSnackBar(message: String,
                              @SnackbarDuration duration: Int)

    fun getString(@StringRes stringRes: Int): String? = activity?.getString(stringRes)
}