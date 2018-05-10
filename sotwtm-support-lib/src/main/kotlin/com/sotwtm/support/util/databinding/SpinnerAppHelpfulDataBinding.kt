package com.sotwtm.support.util.databinding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.widget.Spinner

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [Spinner]
 *
 * @author John
 */
@BindingMethods(
        BindingMethod(type = Spinner::class, attribute = "setAdapter", method = "setAdapter")
)
object SpinnerAppHelpfulDataBinding