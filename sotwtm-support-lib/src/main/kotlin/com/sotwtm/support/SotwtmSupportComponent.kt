package com.sotwtm.support

import android.app.Application
import com.sotwtm.support.scope.LibScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector

@LibScope
@Component(modules = [SotwtmSupportBindsModule::class,
    SotwtmSupportBaseModule::class])
interface SotwtmSupportComponent : AndroidInjector<SotwtmSupportLib> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): SotwtmSupportComponent
    }
}
