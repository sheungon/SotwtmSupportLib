package com.sotwtm.support.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import java.lang.ref.WeakReference

/**
 * Activity applied animation on transit.
 * Created by John on 10/11/2015.

 * @author sheungon
 */
abstract class AppHelpfulDataBindingActivity<DataBindingClass : ViewDataBinding>
    : AppHelpfulActivity(), IOverridePendingTransition {

    override val coordinatorLayoutRef: WeakReference<androidx.coordinatorlayout.widget.CoordinatorLayout?> by lazy {
        WeakReference(
            coordinatorLayoutId?.let {
                dataBinding?.root?.findViewById<androidx.coordinatorlayout.widget.CoordinatorLayout?>(it)
                    ?: findViewById<androidx.coordinatorlayout.widget.CoordinatorLayout?>(it)
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
