package com.sotwtm.support.util.databinding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.widget.Button


/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [Button]
 *
 * @author John
 */
@BindingMethods(
        BindingMethod(type = Button::class, attribute = "android:selected", method = "setSelected")
)
object ButtonAppHelpfulDataBinding