package com.sotwtm.support.dialog

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Bundle
import android.view.View

/**
 * @author John
 */
open class AppHelpfulDialogFragmentViewModel(application: Application) : AndroidViewModel(application) {

    @Volatile
    var isPaused = true
        private set
    @Volatile
    var isViewDestroyed = false
        private set

    @Synchronized
    internal fun syncStatus(viewModel: AppHelpfulDialogFragmentViewModel): AppHelpfulDialogFragmentViewModel {
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