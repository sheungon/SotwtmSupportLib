package com.sotwtm.support.util.databinding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.v7.app.ActionBar

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [ActionBar]
 *
 * @author sheungon
 */
@BindingMethods(
    BindingMethod(
        type = ActionBar::class,
        attribute = "setDisplayShowHomeEnabled",
        method = "setDisplayShowHomeEnabled"
    ),
    BindingMethod(
        type = ActionBar::class,
        attribute = "setDisplayHomeAsUpEnabled",
        method = "setDisplayHomeAsUpEnabled"
    )
)
object ActionBarHelpfulBindingAdapter