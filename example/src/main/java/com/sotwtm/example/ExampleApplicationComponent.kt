package com.sotwtm.example

import com.sotwtm.support.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        AppActivitiesContributorModule::class,
        AppActivitiesClassMapModule::class]
)
interface ExampleApplicationComponent : AndroidInjector<ExampleApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: ExampleApplication): Builder

        fun build(): ExampleApplicationComponent
    }
}