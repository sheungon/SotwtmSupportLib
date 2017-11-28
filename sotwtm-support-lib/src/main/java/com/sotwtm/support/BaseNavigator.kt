package com.sotwtm.support

import com.sotwtm.support.activity.AbHelpfulAppCompatActivity
import com.sotwtm.support.dialog.AbHelpfulDialogFragment
import com.sotwtm.support.fragment.AbHelpfulFragment
import java.lang.ref.WeakReference

/**
 */

abstract class BaseNavigator {

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
    val fragmentDialog: AbHelpfulDialogFragment<*>?
        get() = contextRef?.get() as? AbHelpfulDialogFragment<*>
}
