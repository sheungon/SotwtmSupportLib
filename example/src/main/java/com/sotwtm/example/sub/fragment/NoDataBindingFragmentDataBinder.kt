package com.sotwtm.example.sub.fragment

import android.app.Application
import com.sotwtm.support.fragment.AppHelpfulFragmentDataBinder
import com.sotwtm.support.fragment.FragmentMessenger
import com.sotwtm.support.scope.FragmentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Data binder of [NoDataBindingFragment]
 * @author sheungon
 */
@FragmentScope
class NoDataBindingFragmentDataBinder
@Inject
constructor(
    application: Application,
    private val messenger: FragmentMessenger
) : AppHelpfulFragmentDataBinder(application) {

    override fun onResume() {
        super.onResume()

        messenger.showLoadingDialog("NoDataBindingFragment loading...")
        GlobalScope.launch(Dispatchers.Main) {
            delay(500)
            messenger.dismissLoadingDialog()
        }
    }
}
