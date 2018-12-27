package com.sotwtm.example.sub

import com.sotwtm.support.activity.AppHelpfulActivity
import dagger.Binds
import dagger.Module

/**
 * Here we bind the [SubcomponentActivity] to [AppHelpfulActivity].
 * Because in SotwtmSupportLib [com.sotwtm.support.activity.SimpleActivityNavigator] and
 * [com.sotwtm.support.activity.ActivityMessenger] setMagic [AppHelpfulActivity] to work.
 *
 *
 * @author sheungon
 * */
@Module(
    // We can install other modules needed by SubcomponentActivity here
    includes = []
)
abstract class SubcomponentActivityModule {
    @Binds
    abstract fun activity(activity: SubcomponentActivity): AppHelpfulActivity
}