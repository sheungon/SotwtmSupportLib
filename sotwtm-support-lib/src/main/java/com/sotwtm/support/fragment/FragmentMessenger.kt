package com.sotwtm.support.fragment

import android.support.annotation.StringRes
import com.sotwtm.support.util.SnackbarUtil
import java.lang.ref.WeakReference

/**
 * @author John
 */

class FragmentMessenger(private val fragmentRef: WeakReference<out AppHelpfulFragment<*>>) {
    constructor(_fragment: AppHelpfulFragment<*>) : this(WeakReference(_fragment))

    private val fragment: AppHelpfulFragment<*>?
        get() = fragmentRef.get()

    fun showLoadingDialog() {
        fragment?.showLoadingDialog()
    }

    fun showLoadingDialog(@StringRes msgRes: Int?) {
        fragment?.showLoadingDialog(msgRes)
    }

    fun dismissLoadingDialog() {
        fragment?.dismissLoadingDialog()
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(@StringRes messageRes: Int,
                     @SnackbarUtil.SnackbarDuration duration: Int) {
        fragment?.showSnackBar(messageRes, duration)
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(message: String,
                     @SnackbarUtil.SnackbarDuration duration: Int) {
        fragment?.showSnackBar(message, duration)
    }
}
