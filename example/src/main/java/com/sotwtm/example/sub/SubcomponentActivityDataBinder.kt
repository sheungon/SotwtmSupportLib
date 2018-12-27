package com.sotwtm.example.sub

import android.app.Application
import com.sotwtm.support.activity.AppHelpfulActivityDataBinder
import com.sotwtm.support.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class SubcomponentActivityDataBinder
@Inject
constructor(application: Application,
            @SubcomponentActivitySubcomponent.MagicNumber val magicNumber: Long) : AppHelpfulActivityDataBinder(application)