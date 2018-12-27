package com.sotwtm.example.sub

import android.os.Bundle
import com.sotwtm.example.R
import com.sotwtm.example.databinding.ActivitySubcomponentBinding
import com.sotwtm.support.activity.AppHelpfulDataBindingActivity
import javax.inject.Inject

class SubcomponentActivity: AppHelpfulDataBindingActivity<ActivitySubcomponentBinding>() {

    override val layoutResId: Int = R.layout.activity_subcomponent
    @Inject
    override lateinit var dataBinder: SubcomponentActivityDataBinder
    override val coordinatorLayoutId: Int = R.id.coordinator_layout

    override fun initDataBinding(dataBinding: ActivitySubcomponentBinding, savedInstanceState: Bundle?) {
        dataBinding.dataBinder = dataBinder
    }
}