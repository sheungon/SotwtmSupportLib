package com.sotwtm.support.activity

import android.os.Bundle
import android.support.annotation.AnimRes
import com.sotwtm.support.R

/**
 * View model abstract class for [AbHelpfulAppCompatActivity]
 * @author John
 */
abstract class AbActivityViewModel {

    /**
     * The Enter screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    open val startEnterAnim: Int?
        @AnimRes
        get() = R.anim.slide_in_from_right

    /**
     * The Exit screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    open val startExitAnim: Int?
        @AnimRes
        get() = R.anim.slide_out_to_left

    /**
     * The Enter screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    open val finishEnterAnim: Int?
        @AnimRes
        get() = R.anim.slide_in_from_left

    /**
     * The Exit screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    open val finishExitAnim: Int?
        @AnimRes
        get() = R.anim.slide_out_to_right

    /* Don't name this as "isResumed" because "isResumed" is a hidden final method in parent class. */
    @Volatile
    var isActivityPaused = true
        private set
    @Volatile
    var isActivityDestroyed = false
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

    open fun onSaveInstanceState(outState: Bundle) {
    }

    open fun onRequestPermissionsResult(requestCode: Int,
                                        permissions: Array<out String>,
                                        grantResults: IntArray) {
    }
}
