package com.sotwtm.example.splash

import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.scope.ActivityScope
import dagger.Binds
import dagger.Module

/**
 * Here we bind the [NoDataBindingActivity] to [AppHelpfulActivity].
 * Because in SotwtmSupportLib [com.sotwtm.support.activity.SimpleActivityNavigator] and
 * [com.sotwtm.support.activity.ActivityMessenger] setMagic [AppHelpfulActivity] to work.
 *
 *
 * @author sheungon
 * */
@Module(
    // We can install other modules needed by NoDataBindingActivity here
    includes = []
)
abstract class NoDataBindingActivityModule {
    @Binds
    @ActivityScope
    abstract fun activity(activity: NoDataBindingActivity): AppHelpfulActivity
}
