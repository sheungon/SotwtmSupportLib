package com.sotwtm.example.sub

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.sotwtm.example.sub.fragment.NoDataBindingFragment
import com.sotwtm.example.sub.fragment.SimpleDaggerFragment
import com.sotwtm.example.sub.fragment.sub.SubcomponentFragment
import com.sotwtm.support.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Tab adapter module .
 *
 * @author sheungon
 * */
@Module
class SubcomponentActivityFragmentAdapterModule {

    @Provides
    @ActivityScope
    fun activity(activity: SubcomponentActivity): FragmentPagerAdapter =
        object : FragmentPagerAdapter(activity.supportFragmentManager) {

            override fun getItem(page: Int): Fragment =
                when (page) {
                    0 -> SimpleDaggerFragment()
                    1 -> SubcomponentFragment()
                    else -> NoDataBindingFragment()
                }

            override fun getCount(): Int = 3

            override fun getPageTitle(position: Int): CharSequence? = position.toString()
        }
}