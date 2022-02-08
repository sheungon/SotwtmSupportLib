package com.sotwtm.support.base

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.activity.AppHelpfulDataBindingActivity
import com.sotwtm.support.dialog.AppHelpfulDialogFragment
import com.sotwtm.support.fragment.AppHelpfulFragment
import java.lang.ref.WeakReference

/**
 * A base class of navigator for either [AppHelpfulDataBindingActivity], [AppHelpfulFragment]
 * or [AppHelpfulDialogFragment]
 *
 * @author sheungon
 */

abstract class BaseNavigator {

    constructor(_activity: AppHelpfulActivity) : this(WeakReference(_activity))
    constructor(_fragment: AppHelpfulFragment) : this(WeakReference(_fragment))
    constructor(_fragment: AppHelpfulDialogFragment) : this(WeakReference(_fragment))
    constructor(_contextRef: WeakReference<*>) {
        contextRef = _contextRef
    }

    private val contextRef: WeakReference<*>

    val activity: AppHelpfulActivity?
        get() = contextRef.get() as? AppHelpfulActivity
    val fragment: AppHelpfulFragment?
        get() = contextRef.get() as? AppHelpfulFragment
    val dialogFragment: AppHelpfulDialogFragment?
        get() = contextRef.get() as? AppHelpfulDialogFragment
    val context: Context?
        get() = contextRef.get() as? Context

    fun startActivity(intent: Intent) {
        activity?.startActivity(intent = intent)
            ?: fragment?.startActivity(intent = intent)
            ?: dialogFragment?.startActivity(intent = intent)
    }

    fun startActivity(intent: Intent, options: Bundle?) {
        activity?.startActivity(intent = intent, options = options)
            ?: fragment?.startActivity(intent = intent, options = options)
            ?: dialogFragment?.startActivity(intent = intent, options = options)
    }

    fun startActivity(intent: Intent, overridePendingTransition: Boolean, options: Bundle?) {
        activity?.startActivity(
            intent = intent,
            overridePendingTransition = overridePendingTransition,
            options = options
        )
            ?: fragment?.startActivity(
                intent = intent,
                overridePendingTransition = overridePendingTransition,
                options = options
            )
            ?: dialogFragment?.startActivity(
                intent = intent,
                overridePendingTransition = overridePendingTransition,
                options = options
            )
    }

    fun startActivityForResult(intent: Intent, requestCode: Int) {
        activity?.startActivityForResult(intent = intent, requestCode = requestCode)
            ?: fragment?.startActivityForResult(intent = intent, requestCode = requestCode)
            ?: dialogFragment?.startActivityForResult(intent = intent, requestCode = requestCode)
    }

    fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
        activity?.startActivityForResult(
            intent = intent,
            requestCode = requestCode,
            options = options
        )
            ?: fragment?.startActivityForResult(
                intent = intent,
                requestCode = requestCode,
                options = options
            )
            ?: dialogFragment?.startActivityForResult(
                intent = intent,
                requestCode = requestCode,
                options = options
            )
    }

    fun startActivityForResult(
        intent: Intent,
        requestCode: Int,
        overridePendingTransition: Boolean,
        options: Bundle?
    ) {
        activity?.startActivityForResult(
            intent = intent,
            requestCode = requestCode,
            overridePendingTransition = overridePendingTransition,
            options = options
        )
            ?: fragment?.startActivityForResult(
                intent = intent,
                requestCode = requestCode,
                overridePendingTransition = overridePendingTransition,
                options = options
            )
            ?: dialogFragment?.startActivityForResult(
                intent = intent,
                requestCode = requestCode,
                overridePendingTransition = overridePendingTransition,
                options = options
            )
    }

    fun finishActivity() {
        activity?.finish()
            ?: fragment?.activity?.finish()
            ?: dialogFragment?.activity?.finish()
    }

    fun restartActivity() {
        activity?.recreate()
            ?: fragment?.activity?.recreate()
            ?: dialogFragment?.activity?.recreate()
    }

    fun restartApplication() {
        (activity
            ?: fragment?.activity
            ?: dialogFragment?.activity)?.let { activity ->
            Intent(Intent.ACTION_MAIN).apply {
                setPackage(activity.packageName)
                addCategory(Intent.CATEGORY_LAUNCHER)
            }.let { mainIntent ->
                activity.packageManager.queryIntentActivities(mainIntent, 0)
            }.firstOrNull()?.activityInfo
                ?.let { activityInfo ->
                    PendingIntent.getActivity(
                        activity,
                        1000,
                        Intent().apply {
                            setClassName(activity, activityInfo.name)
                            setPackage(activity.packageName)
                        },
                        PendingIntent.FLAG_CANCEL_CURRENT
                    )?.let { pendingIntent ->
                        (activity.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)
                            ?.set(
                                AlarmManager.RTC,
                                System.currentTimeMillis() + 100,
                                pendingIntent
                            )
                    }
                }
        }

        System.exit(0)
    }
}
