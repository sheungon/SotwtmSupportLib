package com.sotwtm.example

import com.sotwtm.example.simple.SimpleDaggerActivity
import com.sotwtm.example.simple.SimpleDaggerActivityModule
import com.sotwtm.example.splash.NoDataBindingActivity
import com.sotwtm.example.splash.NoDataBindingActivityModule
import com.sotwtm.support.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * All activities with simple contributor
 * @see AppActivitiesClassMapModule
 * */
@Module
abstract class AppActivitiesContributorModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [NoDataBindingActivityModule::class])
    abstract fun noDataBindingActivity(): NoDataBindingActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SimpleDaggerActivityModule::class])
    abstract fun simpleDaggerActivity(): SimpleDaggerActivity
}