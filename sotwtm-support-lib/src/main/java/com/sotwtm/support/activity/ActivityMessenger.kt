package com.sotwtm.support.activity

import android.support.annotation.StringRes
import com.sotwtm.support.R
import com.sotwtm.support.util.SnackbarUtil
import java.lang.ref.WeakReference

/**

 * @author John
 */

class ActivityMessenger(private val activityRef: WeakReference<out AbHelpfulAppCompatActivity<*>>) {
    constructor(_activity: AbHelpfulAppCompatActivity<*>) : this(WeakReference(_activity))

    private val activity: AbHelpfulAppCompatActivity<*>?
        get() = activityRef.get()


    /**
     * @param msgRes The message on loading dialog
     * *
     */
    fun showLoadingDialog(@StringRes msgRes: Int? = R.string.loading) {
        activity?.showLoadingDialog(msgRes)
    }

    fun dismissLoadingDialog() {
        activity?.dismissLoadingDialog()
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(@StringRes messageRes: Int,
                     @SnackbarUtil.SnackbarDuration duration: Int) {
        activity?.showSnackBar(messageRes, duration)
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(message: String,
                     @SnackbarUtil.SnackbarDuration duration: Int) {
        activity?.showSnackBar(message, duration)
    }

    fun getString(@StringRes stringRes: Int,
                  vararg formatArg: Any?): String? {
        return activity?.getString(stringRes, formatArg)
    }
}
