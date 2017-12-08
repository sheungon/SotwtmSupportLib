package com.sotwtm.support.dialog

import android.support.annotation.StringRes
import java.lang.ref.WeakReference

/**
 * @author John
 */

class DialogFragmentMessenger(private val fragmentRef: WeakReference<out AppHelpfulDialogFragment<*>>) {
    constructor(_fragment: AppHelpfulDialogFragment<*>) : this(WeakReference(_fragment))

    private val fragment: AppHelpfulDialogFragment<*>?
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

    fun dismiss() {
        fragment?.dismiss()
    }
}