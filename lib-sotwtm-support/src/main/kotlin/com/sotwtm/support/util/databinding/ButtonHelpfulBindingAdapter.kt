package com.sotwtm.support.util.databinding

import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import android.widget.Button


/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [Button]
 *
 * @author sheungon
 */
@BindingMethods(
    BindingMethod(type = Button::class, attribute = "selected", method = "setSelected")
)
object ButtonHelpfulBindingAdapter