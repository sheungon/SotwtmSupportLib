package com.sotwtm.support.util.databinding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.design.widget.NavigationView

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [NavigationView]
 *
 * @author John
 */
@BindingMethods(
        BindingMethod(type = NavigationView::class, attribute = "android:onNavigationItemSelected", method = "setNavigationItemSelectedListener")
)
object NavigationViewHelpfulDataBinding