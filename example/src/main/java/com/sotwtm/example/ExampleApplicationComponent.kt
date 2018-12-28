package com.sotwtm.example

import com.sotwtm.example.contributor.ActivitiesClassMapModule
import com.sotwtm.example.contributor.ActivitiesContributorModule
import com.sotwtm.example.contributor.FragmentsClassMapModule
import com.sotwtm.example.contributor.FragmentsContributorModule
import com.sotwtm.support.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * All modules needed by this application are installed here.
 * @author sheungon
 * */
@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivitiesContributorModule::class,
        ActivitiesClassMapModule::class,
        FragmentsContributorModule::class,
        FragmentsClassMapModule::class]
)
interface ExampleApplicationComponent : AndroidInjector<ExampleApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: ExampleApplication): Builder

        fun build(): ExampleApplicationComponent
    }
}