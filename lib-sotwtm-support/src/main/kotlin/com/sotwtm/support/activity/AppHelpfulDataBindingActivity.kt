package com.sotwtm.support.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.lang.ref.WeakReference

/**
 * Activity applied animation on transit.
 * Created by John on 10/11/2015.

 * @author sheungon
 */
abstract class AppHelpfulDataBindingActivity<DataBindingClass : ViewDataBinding>
    : AppHelpfulActivity<DataBindingClass>(), IOverridePendingTransition {

    override val coordinatorLayoutRef: WeakReference<androidx.coordinatorlayout.widget.CoordinatorLayout?> by lazy {
        WeakReference(
            coordinatorLayoutId?.let {
                dataBinding?.root?.findViewById(
                    it
                )
                    ?: findViewById(it)
            }
        )
    }

    @Volatile
    var dataBinding: DataBindingClass? = null

    /**
     * Initialize data binding. It will be called on data binding created.
     * @param dataBinding The data binding object bound with this activity's view.
     * @param savedInstanceState The saved instance state of this activity if any.
     * */
    abstract fun initDataBinding(dataBinding: DataBindingClass, savedInstanceState: Bundle?)

    override fun setContentViewInternal(view: View, savedInstanceState: Bundle?) {
        dataBinding?.unbind()
        dataBinding = DataBindingUtil.getBinding(view)

        dataBinding?.let {
            it.lifecycleOwner = this
            initDataBinding(it, savedInstanceState)
        }
    }

    override fun onDestroy() {

        dataBinding?.executePendingBindings()
        dataBinding?.unbind()
        dataBinding = null

        super.onDestroy()
    }

    override val isViewBound: Boolean
        get() = super.isViewBound || dataBinding != null
}
