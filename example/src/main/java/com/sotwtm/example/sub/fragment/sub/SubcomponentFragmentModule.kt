package com.sotwtm.example.sub.fragment.sub

import com.sotwtm.support.fragment.AppHelpfulFragment
import com.sotwtm.support.scope.FragmentScope
import dagger.Binds
import dagger.Module

/**
 * Module to convert fragment to [AppHelpfulFragment]
 *
 * @author sheungon
 * */
@Module(
    includes = []
)
abstract class SubcomponentFragmentModule {
    @Binds
    @FragmentScope
    abstract fun fragment(activity: SubcomponentFragment): AppHelpfulFragment
}
