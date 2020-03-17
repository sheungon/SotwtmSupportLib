package com.sotwtm.support.util.databinding

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.databinding.adapters.ListenerUtil
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [ViewPager]
 *
 * @author sheungon
 */
@BindingMethods(
    BindingMethod(
        type = androidx.viewpager.widget.ViewPager::class,
        attribute = "onTouchListener",
        method = "setOnTouchListener"
    ),
    BindingMethod(
        type = androidx.viewpager.widget.ViewPager::class,
        attribute = "setOffscreenPageLimit",
        method = "setOffscreenPageLimit"
    )
)
object ViewPagerAppHelpfulDataBinding {

    @JvmStatic
    @BindingAdapter(
        value = [
            "setAdapter",
            "setCurrentItem",
            "setTabLayout"],
        requireAll = false
    )
    fun setAdapter(
        view: androidx.viewpager.widget.ViewPager,
        adapter: androidx.viewpager.widget.PagerAdapter?,
        currentItem: Int,
        tabLayout: TabLayout?
    ) {
        view.adapter = adapter

        if (view.currentItem == currentItem) {
            // To trigger the adapter to refresh the selected indicator
            view.currentItem = currentItem + 1
        }
        view.currentItem = currentItem

        tabLayout?.setupWithViewPager(if (view.adapter == null) null else view)
    }

    @JvmStatic
    @BindingAdapter("onPageChange")
    fun onPageChangeListener(
        view: androidx.viewpager.widget.ViewPager,
        listener: androidx.viewpager.widget.ViewPager.OnPageChangeListener?
    ) {
        val oldListener = ListenerUtil.trackListener(view, listener, view.id)
        if (oldListener != null) {
            view.removeOnPageChangeListener(oldListener)
        }
        if (listener != null) {
            view.addOnPageChangeListener(listener)
        }
    }
}