package com.sotwtm.support.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import java.lang.ref.WeakReference

/**
 * Activity applied animation on transit.
 * Created by John on 10/11/2015.

 * @author John
 */
abstract class AppHelpfulActivity<DataBindingClass : ViewDataBinding>
    : AppHelpfulActivityNonDatabinding(), IOverridePendingTransition {

    override val coordinatorLayoutRef: WeakReference<CoordinatorLayout?> by lazy {
        WeakReference(
                coordinatorLayoutId?.let {
                    dataBinding?.root?.findViewById<CoordinatorLayout?>(it)
                            ?: findViewById<CoordinatorLayout?>(it)
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

    override fun setContentViewInternal(layoutResId: Int, savedInstanceState: Bundle?) {
        dataBinding?.unbind()
        dataBinding = DataBindingUtil.setContentView(this, layoutResId)

        dataBinding?.let { initDataBinding(it, savedInstanceState) }
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
