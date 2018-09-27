package com.sotwtm.support.activity

import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.StringRes
import com.sotwtm.support.base.BaseMessenger
import com.sotwtm.support.scope.ActivityScope
import com.sotwtm.support.util.SnackbarUtil
import java.lang.ref.WeakReference
import javax.inject.Inject

/**

 * @author John
 */

@ActivityScope
class ActivityMessenger(private val activityRef: WeakReference<out AppHelpfulActivity>) : BaseMessenger() {
    @Inject
    constructor(_activity: AppHelpfulActivity) : this(WeakReference(_activity))

    override val activity: AppHelpfulActivity?
        get() = activityRef.get()

    /**
     * @param msgRes The message on loading dialog
     * *
     */
    override fun showLoadingDialog(@StringRes msgRes: Int?) {
        activity?.showLoadingDialog(msgRes)
    }

    override fun dismissLoadingDialog() {
        activity?.dismissLoadingDialog()
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    override fun showSnackBar(@StringRes messageRes: Int,
                              @SnackbarDuration duration: Int) {
        activity?.showSnackBar(messageRes, duration)
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    override fun showSnackBar(message: String,
                              @SnackbarDuration duration: Int) {
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
}
