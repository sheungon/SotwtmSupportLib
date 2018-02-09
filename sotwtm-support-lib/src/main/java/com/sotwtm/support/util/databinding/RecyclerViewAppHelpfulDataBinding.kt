package com.sotwtm.support.util.databinding

import android.databinding.BindingAdapter
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.databinding.adapters.ListenerUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sotwtm.support.R

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
    @BindingAdapter(value = ["bind:setLayoutManagerOrientation"])
    fun setLayoutManager(recyclerView: RecyclerView,
                         orientation: Int) {
        val reverseLayout = ListenerUtil.getListener<Boolean?>(recyclerView, R.id.setLayoutManagerReverseLayout)
        val autoMeasureEnabled = ListenerUtil.getListener<Boolean?>(recyclerView, R.id.setAutoMeasureEnabled)
        val layoutManager = LinearLayoutManager(recyclerView.context, orientation, reverseLayout == true)
        if (autoMeasureEnabled != null) {
            layoutManager.isAutoMeasureEnabled = autoMeasureEnabled
        }
        recyclerView.layoutManager = layoutManager
    }

    @JvmStatic
    @BindingAdapter(value = ["bind:setLayoutManagerReverseLayout"])
    fun setLayoutManagerReverseLayout(recyclerView: RecyclerView,
                                      reverseLayout: Boolean?) {
        ListenerUtil.trackListener(recyclerView, reverseLayout, R.id.setLayoutManagerReverseLayout)
        (recyclerView.layoutManager as? LinearLayoutManager)?.reverseLayout = reverseLayout == true
    }

    @JvmStatic
    @BindingAdapter(value = ["bind:setAutoMeasureEnabled"])
    fun setAutoMeasureEnabled(recyclerView: RecyclerView,
                              autoMeasureEnabled: Boolean?) {
        ListenerUtil.trackListener(recyclerView, autoMeasureEnabled, R.id.setAutoMeasureEnabled)
        if (autoMeasureEnabled != null) {
            recyclerView.layoutManager?.isAutoMeasureEnabled = autoMeasureEnabled
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["bind:setNestedScrollingEnabled"])
    fun setNestedScrollingEnabled(recyclerView: RecyclerView,
                                  nestedScrollingEnabled: Boolean?) {
        if (nestedScrollingEnabled != null) {
            recyclerView.isNestedScrollingEnabled = nestedScrollingEnabled
        }
    }
}