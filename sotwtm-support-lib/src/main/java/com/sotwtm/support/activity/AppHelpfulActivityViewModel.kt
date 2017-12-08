package com.sotwtm.support.activity

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.view.MenuItem
import com.sotwtm.support.R

/**
 * View model abstract class for [AppHelpfulActivity]
 * @author John
 */
abstract class AppHelpfulActivityViewModel(app : Application) : AndroidViewModel(app) {

    /**
     * The layout ID for this activity
     */
    @get:LayoutRes
    abstract val layoutResId: Int

    /**
     * The [android.support.v7.widget.Toolbar] view ID in this activity.
     * Override as null if no [android.support.v7.widget.Toolbar] in this activity
     */
    @get:IdRes
    open val toolbarId: Int? = R.id.toolbar

    /**
     * The [android.support.design.widget.CoordinatorLayout] ID in this activity.
     * Override as null if no [android.support.design.widget.CoordinatorLayout] in this activity
     * */
    @get:IdRes
    open val coordinatorLayoutId: Int? = R.id.coordinator_layout

    /**
     * Menu ID for the activity.
     * Override as null if no menu in this activity
     */
    @get:MenuRes
    open val menuResId: Int? = null

    /**
     * The Enter screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    @get:AnimRes
    open val startEnterAnim: Int? = R.anim.slide_in_from_right

    /**
     * The Exit screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    @get:AnimRes
    open val startExitAnim: Int? = R.anim.slide_out_to_left

    /**
     * The Enter screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    @get:AnimRes
    open val finishEnterAnim: Int? = R.anim.slide_in_from_left

    /**
     * The Exit screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    @get:AnimRes
    open val finishExitAnim: Int? = R.anim.slide_out_to_right

    /* Don't name this as "isResumed" because "isResumed" is a hidden final method in parent class. */
    @Volatile
    var isActivityPaused = true
        private set
    @Volatile
    var isActivityDestroyed = false
        private set

    @Synchronized
    internal fun syncStatus(viewModel: AppHelpfulActivityViewModel): AppHelpfulActivityViewModel {
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

    open fun onOptionsItemSelected(item: MenuItem): Boolean = false
}
