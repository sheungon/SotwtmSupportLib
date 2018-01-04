package com.sotwtm.support.util

import android.content.Context
import android.content.res.ColorStateList
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Build
import android.support.annotation.BoolRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import com.sotwtm.util.Log

/**
 * DataBinding methods, BindingAdapter and BindingConversion

 * @author John
 */
@BindingMethods(
        BindingMethod(type = Button::class, attribute = "android:selected", method = "setSelected"),
        BindingMethod(type = ViewPager::class, attribute = "android:onTouchListener", method = "setOnTouchListener"),
        BindingMethod(type = ViewPager::class, attribute = "android:setOffscreenPageLimit", method = "setOffscreenPageLimit"),
        BindingMethod(type = TabLayout::class, attribute = "android:setupWithViewPager", method = "setupWithViewPager"),
        BindingMethod(type = RecyclerView::class, attribute = "android:setAdapter", method = "setAdapter"),
        BindingMethod(type = NavigationView::class, attribute = "android:onNavigationItemSelected", method = "setNavigationItemSelectedListener"),
        BindingMethod(type = Spinner::class, attribute = "android:setAdapter", method = "setAdapter"),
        BindingMethod(type = ActionBar::class, attribute = "android:setDisplayShowHomeEnabled", method = "setDisplayShowHomeEnabled"),
        BindingMethod(type = ActionBar::class, attribute = "android:setDisplayHomeAsUpEnabled", method = "setDisplayHomeAsUpEnabled")
)
object SotwtmDataBindingUtil {

    @JvmStatic
    @BindingAdapter("bind:underlineText")
    fun underLineText(textView: TextView,
                      underline: Boolean) {
        if (underline) {
            textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        }
    }

    @JvmStatic
    @BindingAdapter("bind:addDrawerListener")
    fun addDrawerListener(drawerLayout: DrawerLayout,
                          listener: DrawerLayout.DrawerListener?) {
        if (listener != null) {
            drawerLayout.addDrawerListener(listener)
        }
    }

    @JvmStatic
    @BindingAdapter(value = [
            "bind:setLayoutManagerOrientation",
            "bind:setLayoutManagerReverseLayout",
            "bind:setAutoMeasureEnabled",
            "bind:setNestedScrollingEnabled"],
            requireAll = false)
    fun setLayoutManager(recyclerView: RecyclerView,
                         orientation: Int,
                         reverseLayout: Boolean?,
                         autoMeasureEnabled: Boolean?,
                         nestedScrollingEnabled: Boolean?) {
        val layoutManager = LinearLayoutManager(recyclerView.context, orientation, reverseLayout == true)
        if (autoMeasureEnabled != null) {
            layoutManager.isAutoMeasureEnabled = autoMeasureEnabled
        }
        recyclerView.layoutManager = layoutManager
        if (nestedScrollingEnabled != null) {
            recyclerView.isNestedScrollingEnabled = nestedScrollingEnabled
        }
    }

    @JvmStatic
    @BindingAdapter(value = [
            "bind:setAdapter",
            "bind:setCurrentItem"],
            requireAll = false)
    fun setAdapter(view: ViewPager,
                   adapter: PagerAdapter?,
                   currentItem: Int) {
        view.adapter = adapter

        if (view.currentItem == currentItem) {
            // To trigger the adapter to refresh the selected indicator
            view.currentItem = currentItem + 1
        }
        view.currentItem = currentItem
    }

    @JvmStatic
    @BindingAdapter(value = ["app:setProgressColorRes"])
    fun setProgressBarColorRes(view: ProgressBar,
                               @ColorRes colorRes: Int) {
        setProgressBarColor(view, ContextCompat.getColor(view.context, colorRes))
    }

    @JvmStatic
    @BindingAdapter(value = ["app:setProgressColor"])
    fun setProgressBarColor(view: ProgressBar,
                            @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.progressTintList = ColorStateList.valueOf(color)
        } else {
            view.progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }

    @JvmStatic
    @BindingConversion
    fun stringToDouble(charSequence: CharSequence?): Double {

        try {
            if (charSequence?.isEmpty() != false) {
                return 0.0
            }
            return java.lang.Double.valueOf(charSequence.toString())
        } catch (e: Exception) {
            Log.e("Error on convert charSequence to int : " + charSequence, e)
        }

        return 0.0
    }

    @JvmStatic
    fun getBoolean(context: Context,
                   @BoolRes boolRes: Int): Boolean = context.resources.getBoolean(boolRes)

    @JvmStatic
    @ColorInt
    fun getColor(context: Context,
                 @ColorRes colorRes: Int): Int = ContextCompat.getColor(context, colorRes)

    @JvmStatic
    fun getString(context: Context,
                  @StringRes stringRes: Int): String = context.getString(stringRes)
}
