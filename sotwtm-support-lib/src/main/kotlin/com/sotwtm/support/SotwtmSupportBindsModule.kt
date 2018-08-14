package com.sotwtm.support

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
internal abstract class SotwtmSupportBindsModule {

    @Binds
    abstract fun applicationContext(application: Application): Context
}