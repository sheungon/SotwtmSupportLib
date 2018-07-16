package com.sotwtm.support.dialog

import com.sotwtm.support.BaseMessenger
import com.sotwtm.support.BaseNavigator
import com.sotwtm.support.scope.FragmentScope
import dagger.Binds
import dagger.Module

/**
 * A basic module for all [AppHelpfulDialogFragment] to provide [DialogFragmentMessenger].
 * To use this, a module provides [AppHelpfulDialogFragment] is needed.
 * @author John
 */

@Module
abstract class DialogFragmentMessengerModule {

    @FragmentScope
    @Binds
    abstract fun messenger(messenger: DialogFragmentMessenger): BaseMessenger

    @FragmentScope
    @Binds
    abstract fun navigator(navigator: SimpleDialogFragmentNavigator): BaseNavigator
}
