package com.sotwtm.example.simple

import android.app.Application
import android.content.Intent
import com.sotwtm.example.sub.SubcomponentActivity
import com.sotwtm.support.activity.ActivityMessenger
import com.sotwtm.support.activity.AppHelpfulActivityDataBinder
import com.sotwtm.support.activity.SimpleActivityNavigator
import com.sotwtm.support.scope.ActivityScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Data binder for [SimpleDaggerActivity]
 * @author sheungon
 * */
@ActivityScope
class SimpleDaggerActivityDataBinder
@Inject
constructor(
    application: Application,
    private val messenger: ActivityMessenger,
    private val navigator: SimpleActivityNavigator
) : AppHelpfulActivityDataBinder(application) {

    fun onClickLogin() {
        messenger.showLoadingDialog("Login Loading...")
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            messenger.dismissLoadingDialog()
        }
    }

    fun onClickOpenSubcomponentActivity() {
        navigator.startActivity(Intent(getApplication(), SubcomponentActivity::class.java))
    }
}
