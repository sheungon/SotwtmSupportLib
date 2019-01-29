package com.sotwtm.support.activity

/**
 * Override this to have customized transition effect.
 * This should be implemented on [android.app.Activity] only
 * @author sheungon
 */

interface IOverridePendingTransition {
    fun overridePendingTransitionForStartActivity()
    fun overridePendingTransitionForFinish()
}
