package com.sotwtm.example.sub.fragment

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
abstract class NoDataBindingFragmentModule {
    @Binds
    @FragmentScope
    abstract fun fragment(activity: NoDataBindingFragment): AppHelpfulFragment
}
