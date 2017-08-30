package com.sotwtm.support.activity

import android.content.pm.PackageManager
import android.os.Build
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

    fun checkSelfPermission(permission: String): Int? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity?.checkSelfPermission(permission)
            } else {
                // In previous version always has permission
                PackageManager.PERMISSION_GRANTED
            }

    fun shouldShowRequestPermissionRationale(permission: String): Boolean =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity?.shouldShowRequestPermissionRationale(permission) ?: false
            } else {
                // In previous version always has permission. So, no need to show request permission rationale
                false
            }

    fun requestPermissions(permissions: Array<String>,
                           requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.requestPermissions(permissions, requestCode)
        }
    }


    fun getString(@StringRes stringRes: Int,
                  vararg formatArg: Any?): String? = activity?.getString(stringRes, formatArg)
}
