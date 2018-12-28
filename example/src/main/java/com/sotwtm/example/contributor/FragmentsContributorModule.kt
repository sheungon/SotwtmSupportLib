package com.sotwtm.example.contributor

import com.sotwtm.example.sub.fragment.NoDataBindingFragment
import com.sotwtm.example.sub.fragment.NoDataBindingFragmentModule
import com.sotwtm.example.sub.fragment.SimpleDaggerFragment
import com.sotwtm.example.sub.fragment.SimpleDaggerFragmentModule
import com.sotwtm.support.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * All fragments with simple contributor are registered here
 * @see FragmentsClassMapModule
 * @author sheungon
 * */
@Module
abstract class FragmentsContributorModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [NoDataBindingFragmentModule::class])
    abstract fun noDataBindingFragment(): NoDataBindingFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SimpleDaggerFragmentModule::class])
    abstract fun simpleDaggerFragment(): SimpleDaggerFragment
}
