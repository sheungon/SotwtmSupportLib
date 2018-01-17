package com.sotwtm.support.util.databinding

import android.databinding.BindingAdapter
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [RecyclerView]
 *
 * @author John
 */
@BindingMethods(
        BindingMethod(type = RecyclerView::class, attribute = "android:setAdapter", method = "setAdapter")
)
object RecyclerViewAppHelpfulDataBinding {

    @JvmStatic
    @BindingAdapter(value = [
        "bind:setLayoutManagerOrientation",
        "bind:setLayoutManagerReverseLayout",
        "bind:setAutoMeasureEnabled",
        "bind:setNestedScrollingEnabled"],
            requireAll = false)
    fun setLayoutManager(recyclerView: RecyclerView,
                         orientation: Int,
                         reverseLayout: Boolean?,
                         autoMeasureEnabled: Boolean?,
                         nestedScrollingEnabled: Boolean?) {
        val layoutManager = LinearLayoutManager(recyclerView.context, orientation, reverseLayout == true)
        if (autoMeasureEnabled != null) {
            layoutManager.isAutoMeasureEnabled = autoMeasureEnabled
        }
        recyclerView.layoutManager = layoutManager
        if (nestedScrollingEnabled != null) {
            recyclerView.isNestedScrollingEnabled = nestedScrollingEnabled
        }
    }
}