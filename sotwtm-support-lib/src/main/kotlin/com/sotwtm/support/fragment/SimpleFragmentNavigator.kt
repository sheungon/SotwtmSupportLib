package com.sotwtm.support.fragment

import com.sotwtm.support.BaseNavigator
import javax.inject.Inject

class SimpleFragmentNavigator
@Inject
constructor(fragment: AppHelpfulFragment) : BaseNavigator(fragment)
