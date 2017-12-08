package com.sotwtm.support.fragment

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.LayoutRes
import android.view.View
import com.sotwtm.support.R

/**
 * @author John
 */

abstract class AppHelpfulFragmentViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * The layout ID for this fragment
     */
    @get:LayoutRes
    abstract val layoutResId: Int

    /**
     * The Enter screen animation to override on start activity
     * @return 0 means no animation the activity animation
     * *
     */
    open val startEnterAnim: Int
        @AnimRes
        get() = R.anim.fragment_slide_in_from_right

    /**
     * The Exit screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    open val startExitAnim: Int
        @AnimRes
        get() = R.anim.fragment_slide_out_to_left

    /**
     * The Enter screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    open val finishEnterAnim: Int
        @AnimRes
        get() = R.anim.fragment_slide_in_from_left

    /**
     * The Exit screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    open val finishExitAnim: Int
        @AnimRes
        get() = R.anim.fragment_slide_out_to_right

    @Volatile
    var isPaused = true
        private set
    @Volatile
    var isViewDestroyed = false
        private set

    @Synchronized
    internal fun syncStatus(viewModel: AppHelpfulFragmentViewModel): AppHelpfulFragmentViewModel {
        isPaused = viewModel.isPaused
        isViewDestroyed = viewModel.isViewDestroyed
        return this
    }

    open fun onCreate() {
    }

    internal fun onViewCreatedInternal(view: View?, savedInstanceState: Bundle?) {
        isViewDestroyed = false
        onViewCreated(view, savedInstanceState)
    }

    open fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    }

    open fun onStart() {
    }

    internal fun onResumeInternal() {
        isPaused = false
        onResume()
    }

    open fun onResume() {
    }

    internal fun onPauseInternal() {
        isPaused = true
        onPause()
    }

    open fun onPause() {
    }

    open fun onStop() {
    }

    internal fun onDestroyViewInternal() {
        isViewDestroyed = true
        onDestroyView()
    }

    open fun onDestroyView() {
    }

    open fun onDestroy() {
    }
}
