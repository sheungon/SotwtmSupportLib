package com.sotwtm.support.activity

import com.sotwtm.support.BaseNavigator
import javax.inject.Inject

class SimpleActivityNavigator
@Inject
constructor(activity: AppHelpfulActivity) : BaseNavigator(activity)
