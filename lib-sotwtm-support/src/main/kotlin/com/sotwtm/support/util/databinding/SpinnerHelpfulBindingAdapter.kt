package com.sotwtm.support.util.databinding

import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import android.widget.Spinner

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [Spinner]
 *
 * @author sheungon
 */
@BindingMethods(
    BindingMethod(type = Spinner::class, attribute = "setAdapter", method = "setAdapter")
)
object SpinnerHelpfulBindingAdapter