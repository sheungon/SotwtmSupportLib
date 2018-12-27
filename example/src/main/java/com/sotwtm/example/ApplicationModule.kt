package com.sotwtm.example

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun application(app: ExampleApplication) : Application

    @Binds
    abstract fun context(app: ExampleApplication): Context
}