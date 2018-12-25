package com.sotwtm.support.util.databinding

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.InverseBindingMethod
import android.databinding.InverseBindingMethods
import android.databinding.adapters.ListenerUtil
import android.widget.AdapterView
import android.widget.ArrayAdapter

@InverseBindingMethods(
        InverseBindingMethod(type = AdapterView::class,
                attribute = "selectedItemString",
                method = "getSelectedItem",
                event = "android:selectedItemPositionAttrChanged")
)
object AdapterViewHelpfulDataBinding {

    @JvmStatic
    @BindingAdapter("itemsStringArrayRes")
    fun AdapterView<*>.setItemsStringArrayRes(stringArrayRes: Int?) {
        val arrayAdapter = stringArrayRes?.let { arrayRes ->
            ListenerUtil.getListener<ArrayAdapter<String>>(this, arrayRes)
                    ?: run {
                        arrayRes.toResStringArray(context)
                                .toArrayAdapter(context).apply {
                                    ListenerUtil.trackListener(this@setItemsStringArrayRes, this, arrayRes)
                                }

                    }
        }
        if (adapter != arrayAdapter) {
            adapter = arrayAdapter
        }
    }

    @JvmStatic
    @BindingAdapter("selectedItemString")
    fun AdapterView<*>.selectedItemString(selectedString: String?) {
        val selectedPos = (adapter as? ArrayAdapter<String>)
                ?.run {
                    for (i in 0 until count) {
                        if (getItem(i) == selectedString) {
                            return@run i
                        }
                    }
                    -1
                } ?: -1
        if (selectedItemPosition != selectedPos) {
            setSelection(selectedPos)
        }
    }

    @JvmStatic
    @BindingAdapter("itemsStringArrayRes", "selectedItemString")
    fun AdapterView<*>.selectedItemString(stringArrayRes: Int?, selectedString: String?) {
        setItemsStringArrayRes(stringArrayRes)
        selectedItemString(selectedString)
    }

    private fun Int.toResStringArray(context: Context) = context.resources.getStringArray(this).asList()

    private fun List<String>.toArrayAdapter(context: Context) =
            ArrayAdapter(context, android.R.layout.simple_spinner_item, this).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
}