package com.sotwtm.support.util.databinding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.design.widget.TabLayout

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [TabLayout]
 *
 * @author John
 */
@BindingMethods(
        BindingMethod(type = TabLayout::class, attribute = "setupWithViewPager", method = "setupWithViewPager")
)
object TabLayoutAppHelpfulDataBinding