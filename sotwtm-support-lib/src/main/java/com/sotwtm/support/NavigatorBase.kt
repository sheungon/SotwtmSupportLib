package com.sotwtm.support

import android.support.annotation.StringRes
import com.sotwtm.support.activity.AbHelpfulAppCompatActivity
import com.sotwtm.support.fragment.AbHelpfulFragment
import com.sotwtm.support.util.SnackbarUtil
import java.lang.ref.WeakReference

/**
 */

abstract class NavigatorBase {

    constructor(_activity: AbHelpfulAppCompatActivity<*>) : this(WeakReference(_activity))
    constructor(_fragment: AbHelpfulFragment<*>) : this(WeakReference(_fragment))
    constructor(_contextRef: WeakReference<*>) {
        contextRef = _contextRef
    }

    private val contextRef: WeakReference<*>?

    val activity: AbHelpfulAppCompatActivity<*>?
        get() = contextRef?.get() as? AbHelpfulAppCompatActivity<*>
    val fragment: AbHelpfulFragment<*>?
        get() = contextRef?.get() as? AbHelpfulFragment<*>

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(@StringRes messageRes: Int,
                     @SnackbarUtil.SnackbarDuration duration: Int) {
        activity?.showSnackBar(messageRes, duration)
                ?: fragment?.showSnackBar(messageRes, duration)
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(message: String,
                     @SnackbarUtil.SnackbarDuration duration: Int) {
        activity?.showSnackBar(message, duration)
                ?: fragment?.showSnackBar(message, duration)
    }
}
