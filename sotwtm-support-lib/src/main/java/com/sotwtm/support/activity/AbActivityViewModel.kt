package com.sotwtm.support.activity

import android.os.Bundle

/**
 * View model abstract class for [AbHelpfulAppCompatActivity]
 * @author John
 */
abstract class AbActivityViewModel {

    /* Don't name this as "isResumed" because "isResumed" is a hidden final method in parent class. */
    @Volatile var isActivityPaused = true
        private set
    @Volatile var isActivityDestroyed = false
        private set

    @Synchronized
    internal fun syncStatus(viewModel: AbActivityViewModel): AbActivityViewModel {
        isActivityPaused = viewModel.isActivityPaused
        isActivityDestroyed = viewModel.isActivityDestroyed
        return this
    }

    internal fun onCreateInternal(savedInstanceState: Bundle?) {
        isActivityDestroyed = false
        onCreate(savedInstanceState)
    }

    open fun onCreate(savedInstanceState: Bundle?) {
    }

    open fun onStart() {
    }

    internal fun onResumeInternal() {
        isActivityPaused = false
        onResume()
    }

    open fun onResume() {
    }

    internal fun onPauseInternal() {
        isActivityPaused = true
        onPause()
    }

    open fun onPause() {
    }

    open fun onStop() {
    }

    internal fun onDestroyInternal() {
        isActivityDestroyed = true
        onDestroy()
    }

    open fun onDestroy() {
    }
}
