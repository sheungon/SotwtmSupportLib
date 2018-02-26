package com.sotwtm.support.util.databinding

import android.databinding.BindingAdapter
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [ViewPager]
 *
 * @author John
 */
@BindingMethods(
        BindingMethod(type = ViewPager::class, attribute = "android:onTouchListener", method = "setOnTouchListener"),
        BindingMethod(type = ViewPager::class, attribute = "android:setOffscreenPageLimit", method = "setOffscreenPageLimit")
)
object ViewPagerAppHelpfulDataBinding {

    @JvmStatic
    @BindingAdapter(value = [
        "bind:setAdapter",
        "bind:setCurrentItem",
        "app:setTabLayout"],
            requireAll = false)
    fun setAdapter(view: ViewPager,
                   adapter: PagerAdapter?,
                   currentItem: Int,
                   tabLayout: TabLayout?) {
        view.adapter = adapter

        if (view.currentItem == currentItem) {
            // To trigger the adapter to refresh the selected indicator
            view.currentItem = currentItem + 1
        }
        view.currentItem = currentItem

        tabLayout?.setupWithViewPager(if (view.adapter == null) null else view)
    }
}