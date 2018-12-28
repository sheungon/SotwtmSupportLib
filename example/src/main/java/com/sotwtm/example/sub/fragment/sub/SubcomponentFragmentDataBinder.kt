package com.sotwtm.example.sub.fragment.sub

import android.app.Application
import com.sotwtm.support.fragment.AppHelpfulFragment
import com.sotwtm.support.fragment.AppHelpfulFragmentDataBinder
import com.sotwtm.support.scope.FragmentScope
import javax.inject.Inject


/**
 * Data binder of [SubcomponentFragment]
 * @author sheungon
 */
@FragmentScope
class SubcomponentFragmentDataBinder
@Inject
constructor(
    application: Application,
    fragment: AppHelpfulFragment,
    @SubcomponentFragmentSubcomponent.MagicString magicString: String
) : AppHelpfulFragmentDataBinder(application) {

    val activityName: String? = "${fragment.activity?.localClassName}|$magicString"
}