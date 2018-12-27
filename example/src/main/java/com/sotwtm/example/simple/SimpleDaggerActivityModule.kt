package com.sotwtm.example.simple

import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.scope.ActivityScope
import dagger.Binds
import dagger.Module

/**
 * Here we bind the [SimpleDaggerActivity] to [AppHelpfulActivity].
 * Because in SotwtmSupportLib [com.sotwtm.support.activity.SimpleActivityNavigator] and
 * [com.sotwtm.support.activity.ActivityMessenger] setMagic [AppHelpfulActivity] to work.
 *
 *
 * @author sheungon
 * */
@Module(
    // We can install other modules needed by SimpleDaggerActivity here
    includes = []
)
abstract class SimpleDaggerActivityModule {
    @Binds
    @ActivityScope
    abstract fun activity(activity: SimpleDaggerActivity): AppHelpfulActivity
}
