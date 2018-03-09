package com.sotwtm.support.dialog

import android.support.annotation.StringRes
import android.widget.Toast
import com.sotwtm.util.Log
import java.lang.ref.WeakReference

/**
 * @author John
 */

class DialogFragmentMessenger(private val fragmentRef: WeakReference<out AppHelpfulDialogFragment<*>>) {
    constructor(_fragment: AppHelpfulDialogFragment<*>) : this(WeakReference(_fragment))

    private val fragment: AppHelpfulDialogFragment<*>?
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

    fun dismiss() {
        try {
            fragment?.dismiss()
        } catch (e: Exception) {
            Log.e("Error on dismiss", e)
        }
    }

    fun showToast(stringRes: Int, duration: Int) {
        Toast.makeText(fragment?.context ?: return,
                stringRes,
                duration).show()
    }
}
