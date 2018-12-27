package com.sotwtm.support.util.databinding

import android.databinding.*
import android.databinding.adapters.ListenerUtil
import android.support.design.widget.TabLayout


/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [TabLayout]
 *
 * @author sheunogn
 */
@BindingMethods(
        BindingMethod(type = TabLayout::class, attribute = "setupWithViewPager", method = "setupWithViewPager")
)
object TabLayoutAppHelpfulDataBinding {

    @JvmStatic
    @BindingAdapter("selectTabAt")
    fun selectTab(view: TabLayout,
                  selectedTab: Int) {
        view.getTabAt(selectedTab)?.select()
    }

    @Synchronized
    @JvmStatic
    @BindingAdapter(value = ["onTabSelected", "selectTabChanged"],
            requireAll = false)
    fun setTabChangeListener(view: TabLayout,
                             listener: TabLayout.OnTabSelectedListener?,
                             inverseBindingListener: InverseBindingListener?) {
        val oldListener: TabLayout.OnTabSelectedListener? = ListenerUtil.getListener(view, view.id)
        if (oldListener != null) {
            view.removeOnTabSelectedListener(oldListener)
        }
        if (inverseBindingListener != null) {
            val listenerWrapper = object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    inverseBindingListener.onChange()
                    listener?.onTabReselected(tab)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    listener?.onTabUnselected(tab)
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    inverseBindingListener.onChange()
                    listener?.onTabSelected(tab)
                }

            }
            ListenerUtil.trackListener(view, listenerWrapper, view.id)
            view.addOnTabSelectedListener(listenerWrapper)
        } else if (listener != null) {
            ListenerUtil.trackListener(view, listener, view.id)
            view.addOnTabSelectedListener(listener)
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectTabAt", event = "selectTabChanged")
    fun getScrollY(view: TabLayout): Int = view.selectedTabPosition
}