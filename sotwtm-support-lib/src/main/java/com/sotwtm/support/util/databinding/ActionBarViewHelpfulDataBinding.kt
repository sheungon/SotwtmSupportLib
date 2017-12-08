package com.sotwtm.support.util.databinding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.v7.app.ActionBar

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [ActionBar]
 *
 * @author John
 */
@BindingMethods(
        BindingMethod(type = ActionBar::class, attribute = "android:setDisplayShowHomeEnabled", method = "setDisplayShowHomeEnabled"),
        BindingMethod(type = ActionBar::class, attribute = "android:setDisplayHomeAsUpEnabled", method = "setDisplayHomeAsUpEnabled")
)
object ActionBarViewHelpfulDataBinding