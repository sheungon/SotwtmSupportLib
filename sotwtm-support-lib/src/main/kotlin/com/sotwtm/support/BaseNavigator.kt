package com.sotwtm.support

import android.content.Context
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

    private val contextRef: WeakReference<*>?

    val activity: AppHelpfulActivity?
        get() = contextRef?.get() as? AppHelpfulActivity
    val fragment: AppHelpfulFragment?
        get() = contextRef?.get() as? AppHelpfulFragment
    val dialogFragment: AppHelpfulDialogFragment?
        get() = contextRef?.get() as? AppHelpfulDialogFragment
    val context: Context?
        get() = contextRef?.get() as? Context
}
