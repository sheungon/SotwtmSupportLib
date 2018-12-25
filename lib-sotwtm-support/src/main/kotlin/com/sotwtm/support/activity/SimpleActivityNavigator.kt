package com.sotwtm.support.activity

import com.sotwtm.support.base.BaseNavigator
import com.sotwtm.support.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class SimpleActivityNavigator
@Inject
constructor(activity: AppHelpfulActivity) : BaseNavigator(activity)
