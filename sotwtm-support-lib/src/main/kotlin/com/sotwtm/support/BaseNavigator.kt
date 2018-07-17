package com.sotwtm.support

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
 * @author John
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
        activity?.startActivity(intent)
                ?: fragment?.startActivity(intent)
                ?: dialogFragment?.startActivity(intent)
    }

    fun startActivity(intent: Intent, options: Bundle?) {
        activity?.startActivity(intent, options)
                ?: fragment?.startActivity(intent, options)
                ?: dialogFragment?.startActivity(intent, options)
    }

    fun startActivity(intent: Intent, overridePendingTransition: Boolean, options: Bundle?) {
        activity?.startActivity(intent = intent,
                overridePendingTransition = overridePendingTransition,
                options = options)
                ?: fragment?.startActivity(intent = intent,
                        overridePendingTransition = overridePendingTransition,
                        options = options)
                ?: dialogFragment?.startActivity(intent = intent,
                        overridePendingTransition = overridePendingTransition,
                        options = options)
    }

    fun startActivityForResult(intent: Intent, requestCode: Int) {
        activity?.startActivityForResult(intent, requestCode)
                ?: fragment?.startActivityForResult(intent, requestCode)
                ?: dialogFragment?.startActivityForResult(intent, requestCode)
    }

    fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
        activity?.startActivityForResult(intent, requestCode, options)
                ?: fragment?.startActivityForResult(intent, requestCode, options)
                ?: dialogFragment?.startActivityForResult(intent, requestCode, options)
    }

    fun startActivityForResult(intent: Intent, requestCode: Int, overridePendingTransition: Boolean, options: Bundle?) {
        activity?.startActivityForResult(intent = intent,
                requestCode = requestCode,
                overridePendingTransition = overridePendingTransition,
                options = options)
                ?: fragment?.startActivityForResult(intent = intent,
                        requestCode = requestCode,
                        overridePendingTransition = overridePendingTransition,
                        options = options)
                ?: dialogFragment?.startActivityForResult(intent = intent,
                        requestCode = requestCode,
                        overridePendingTransition = overridePendingTransition,
                        options = options)
    }
}
