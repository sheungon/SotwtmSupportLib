package com.sotwtm.example.sub

import com.sotwtm.support.scope.ActivityScope
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Qualifier

@ActivityScope
@Subcomponent(modules = [SubcomponentActivityModule::class])
interface SubcomponentActivitySubcomponent : AndroidInjector<SubcomponentActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SubcomponentActivity>() {
        @BindsInstance
        abstract fun setMagic(@MagicNumber magicNumber: Long): Builder
    }

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class MagicNumber
}