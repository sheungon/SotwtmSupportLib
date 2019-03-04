package com.sotwtm.support.fragment

import com.sotwtm.support.base.BaseNavigator
import com.sotwtm.support.scope.FragmentScope
import javax.inject.Inject

/**
 * A simple navigator for [AppHelpfulFragment]
 * @author sheungon
 * */
@FragmentScope
class SimpleFragmentNavigator
@Inject
constructor(fragment: AppHelpfulFragment) : BaseNavigator(fragment)
