package com.sotwtm.example.sub.fragment.sub

import com.sotwtm.support.scope.FragmentScope
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Qualifier

@FragmentScope
@Subcomponent(
    modules = [SubcomponentFragmentModule::class]
)
interface SubcomponentFragmentSubcomponent : AndroidInjector<SubcomponentFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SubcomponentFragment>() {
        @BindsInstance
        abstract fun setMagic(@MagicString magicString: String): Builder
    }

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class MagicString
}