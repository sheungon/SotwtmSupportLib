package com.sotwtm.support.fragment

import android.support.annotation.StringRes
import com.sotwtm.support.util.SnackbarUtil
import com.sotwtm.util.Log
import java.lang.ref.WeakReference

/**
 * @author John
 */

class FragmentMessenger(private val fragmentRef: WeakReference<out AppHelpfulFragment<*>>) {
    constructor(_fragment: AppHelpfulFragment<*>) : this(WeakReference(_fragment))

    private val fragment: AppHelpfulFragment<*>?
        get() = fragmentRef.get()

    fun showLoadingDialog() {
        try {
            fragment?.showLoadingDialog()
        } catch (e: Exception) {
            Log.e("Error on showLoadingDialog", e)
        }
    }

    fun showLoadingDialog(@StringRes msgRes: Int?) {
        try {
            fragment?.showLoadingDialog(msgRes)
        } catch (e: Exception) {
            Log.e("Error on showLoadingDialog", e)
        }
    }

    fun dismissLoadingDialog() {
        try {
            fragment?.dismissLoadingDialog()
        } catch (e: Exception) {
            Log.e("Error on dismissLoadingDialog", e)
        }
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(@StringRes messageRes: Int,
                     @SnackbarUtil.SnackbarDuration duration: Int) {
        try {
            fragment?.showSnackBar(messageRes, duration)
        } catch (e: Exception) {
            Log.e("Error on showSnackBar", e)
        }
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(message: String,
                     @SnackbarUtil.SnackbarDuration duration: Int) {
        try {
            fragment?.showSnackBar(message, duration)
        } catch (e: Exception) {
            Log.e("Error on showSnackBar", e)
        }
    }
}
