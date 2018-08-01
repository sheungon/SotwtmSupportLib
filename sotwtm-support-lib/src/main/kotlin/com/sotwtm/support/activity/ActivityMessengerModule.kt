package com.sotwtm.support.activity

import com.sotwtm.support.base.BaseMessenger
import com.sotwtm.support.base.BaseNavigator
import com.sotwtm.support.scope.ActivityScope
import dagger.Binds
import dagger.Module

/**
 * A basic module for all [AppHelpfulActivity] to provide [ActivityMessenger].
 * To use this, a module provides [AppHelpfulActivity] is needed.
 * @author John
 */
@Module
abstract class ActivityMessengerModule {

    @ActivityScope
    @Binds
    abstract fun messenger(messenger: ActivityMessenger): BaseMessenger

    @ActivityScope
    @Binds
    abstract fun navigator(navigator: SimpleActivityNavigator): BaseNavigator
}