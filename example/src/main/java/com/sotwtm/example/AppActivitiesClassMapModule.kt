package com.sotwtm.example

import com.sotwtm.example.sub.SubcomponentActivity
import com.sotwtm.example.sub.SubcomponentActivitySubcomponent
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import java.util.*
import kotlin.random.Random

/**
 * All activities with subcomponent
 * @see AppActivitiesContributorModule
 * */
@Module(
    // Register activity subcomponents here
    subcomponents = [SubcomponentActivitySubcomponent::class]
)
class AppActivitiesClassMapModule {

    @Provides
    @IntoMap
    @ClassKey(SubcomponentActivity::class)
    fun bindActivityInjectorFactory(builder: SubcomponentActivitySubcomponent.Builder): AndroidInjector.Factory<*> =
        // Set any needed values for the subcomponent here
        builder.apply {
            builder.setMagic(Random(Date().time).nextLong())
        }
}
