package com.sotwtm.support.fragment

import com.sotwtm.support.BaseMessenger
import com.sotwtm.support.BaseNavigator
import com.sotwtm.support.scope.FragmentScope
import dagger.Binds
import dagger.Module

/**
 * A basic module for all [AppHelpfulFragment] to provide [FragmentMessenger].
 * To use this, a module provides [AppHelpfulFragment] is needed.
 * @author John
 */

@Module
abstract class FragmentMessengerModule {

    @FragmentScope
    @Binds
    abstract fun messenger(fragment: FragmentMessenger): BaseMessenger

    @FragmentScope
    @Binds
    abstract fun navigator(navigator: SimpleFragmentNavigator): BaseNavigator
}
