package com.sotwtm.support.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.sotwtm.util.Log


/**
 * UI Utils for handling UI actions or calculation.
 * @author John
 */
object UIUtil {

    fun hideSoftKeyboard(fragment: Fragment) {
        fragment.activity?.let {
            hideSoftKeyboard(it)
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        activity.currentFocus?.let { hideSoftKeyboard(activity, it) }
                ?: Log.w("Cannot hide keyboard as no focus in the provided activity.")
    }

    /**@return The fragment tag of the fragment at position in a view pager.
     */
    fun getViewPagerFragmentTag(@IdRes viewPagerId: Int, pageId: Long): String {
        return "android:switcher:$viewPagerId:$pageId"
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun hideNavigationBar(activity: Activity) {

        // Hide navigation bar
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION /*hide nav bar*/ or
                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) View.SYSTEM_UI_FLAG_IMMERSIVE else 0)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun hideStatusBar(activity: Activity) {

        // Hide status bar
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_FULLSCREEN /*hide nav bar*/ or
                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) View.SYSTEM_UI_FLAG_IMMERSIVE else 0)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun hideNavigationAndStatusBar(activity: Activity) {

        // Full screen
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_FULLSCREEN /*hide nav bar*/ or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION /*hide nav bar*/ or
                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) View.SYSTEM_UI_FLAG_IMMERSIVE else 0)
    }

    fun isCurrentlyLandscape(context: Context): Boolean =
            context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun dpToPixel(context: Context, dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)

    internal fun hideSoftKeyboard(context: Context,
                                  currentFocusView: View) {

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
    }
}
