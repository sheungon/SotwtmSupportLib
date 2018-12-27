package com.sotwtm.support.util.databinding

import android.databinding.BindingAdapter
import android.view.View
import com.sotwtm.support.activity.AppHelpfulActivity

/**
 * @author sheunogn
 */
object AppHelpfulBindingAdapter {

    @JvmStatic
    @BindingAdapter("menuId")
    fun showAllMenu(view: View,
                    menuId: Int?) {
        (view.context as? AppHelpfulActivity)?.menuResId = menuId
    }
}