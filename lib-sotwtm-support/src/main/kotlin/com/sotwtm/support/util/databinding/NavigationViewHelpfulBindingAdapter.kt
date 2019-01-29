package com.sotwtm.support.util.databinding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.design.widget.NavigationView

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [NavigationView]
 *
 * @author sheungon
 */
@BindingMethods(
    BindingMethod(
        type = NavigationView::class,
        attribute = "onNavigationItemSelected",
        method = "setNavigationItemSelectedListener"
    )
)
object NavigationViewHelpfulBindingAdapter