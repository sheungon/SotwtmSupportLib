package com.sotwtm.support.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.annotation.*
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.sotwtm.support.R
import com.sotwtm.support.dialog.LoadingDialogFragment
import com.sotwtm.support.util.SnackbarUtil
import com.sotwtm.support.util.UIUtil
import com.sotwtm.util.Log
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicReference

/**
 * Activity applied animation on transit.
 * Created by John on 10/11/2015.

 * @author John
 */
abstract class AppHelpfulActivity<DataBindingClass : ViewDataBinding>
    : AppCompatActivity(), IOverridePendingTransition {

    /**
     * The layout ID for this activity
     */
    @get:LayoutRes
    abstract val layoutResId: Int

    /**
     * The [android.support.v7.widget.Toolbar] view ID in this activity.
     * Override as null if no [android.support.v7.widget.Toolbar] in this activity
     */
    @get:IdRes
    open val toolbarId: Int? = R.id.toolbar

    /**
     * The [android.support.design.widget.CoordinatorLayout] ID in this activity.
     * Override as null if no [android.support.design.widget.CoordinatorLayout] in this activity
     * */
    @get:IdRes
    open val coordinatorLayoutId: Int? = R.id.coordinator_layout

    /**
     * Menu ID for the activity.
     * Override as null if no menu in this activity
     */
    @get:MenuRes
    open var menuResId: Int? = null
        set(value) {
            field = value
            if (value != null) {
                toolbarId?.let {
                    findViewById<Toolbar?>(it)?.menu?.let { menu ->
                        menuInflater?.inflate(value, menu)
                    }
                }
            }
        }

    /**
     * The Enter screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    @get:AnimRes
    open val startEnterAnim: Int? = R.anim.slide_in_from_right

    /**
     * The Exit screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    @get:AnimRes
    open val startExitAnim: Int? = R.anim.slide_out_to_left

    /**
     * The Enter screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    @get:AnimRes
    open val finishEnterAnim: Int? = R.anim.slide_in_from_left

    /**
     * The Exit screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    @get:AnimRes
    open val finishExitAnim: Int? = R.anim.slide_out_to_right

    protected val coordinatorLayoutRef: WeakReference<CoordinatorLayout?> by lazy {
        WeakReference(
                coordinatorLayoutId?.let {
                    dataBinding?.root?.findViewById<CoordinatorLayout?>(it)
                }
        )
    }
    protected val rootView: WeakReference<View?> by lazy {
        WeakReference(
                findViewById<View?>(android.R.id.content)
        )
    }
    protected val savedInstanceStateRef = AtomicReference<Bundle?>()

    private var actionBarTitle: String? = null

    @StringRes
    private var loadingDialogMsg: Int? = NONE

    private var backStackListener: MyOnBackStackChangedListener? = null

    @Volatile
    var dataBinding: DataBindingClass? = null
        private set
    private var fullScreenFlag = 0x0

    /**
     * Indicate if there should be back button on toolbar
     * */
    open val menuBackEnabled: Boolean = false
    abstract val viewModel: AppHelpfulActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceStateRef.set(savedInstanceState)
        super.onCreate(savedInstanceState)

        dataBinding?.unbind()
        dataBinding = DataBindingUtil.setContentView(this, layoutResId)

        toolbarId?.let { toolbarId ->
            val toolbar = findViewById<Toolbar?>(toolbarId)
            if (toolbar != null) {
                setSupportActionBar(toolbar)
            }
        }

        viewModel.onCreateInternal(savedInstanceState)

        backStackListener = MyOnBackStackChangedListener(this)

        val decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener(MyOnSystemUiVisibilityChangeListener(this))
    }

    override fun onStart() {
        super.onStart()

        updateActionBarHomeButton()

        supportFragmentManager?.addOnBackStackChangedListener(backStackListener)

        viewModel.onStart()
    }

    override fun onResume() {
        super.onResume()

        updateFullScreenStatus()

        viewModel.onResumeInternal()
        if (loadingDialogMsg == NONE) {
            dismissLoadingDialog()
        } else {
            showLoadingDialog(loadingDialogMsg)
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.onPauseInternal()
    }

    override fun onStop() {
        super.onStop()

        viewModel.onStop()

        supportFragmentManager?.removeOnBackStackChangedListener(backStackListener)
    }

    override fun onDestroy() {
        super.onDestroy()

        dataBinding?.executePendingBindings()
        dataBinding?.unbind()
        dataBinding = null

        viewModel.onDestroyInternal()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        viewModel.onSaveInstanceState(outState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            updateFullScreenStatus()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        supportActionBar?.let {
            if (actionBarTitle != null) {
                it.setDisplayShowTitleEnabled(true)
                it.title = actionBarTitle
            }
        }

        menuResId?.let {
            menuInflater.inflate(it, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> viewModel.onOptionsItemSelected(item)
            }

    override fun onBackPressed() {

        val fragmentManager = supportFragmentManager
        val backStackEntryCount = fragmentManager.backStackEntryCount

        if (backStackEntryCount == 0) {
            // Call the default action
            try {
                super.onBackPressed()
            } catch (e: Exception) {
                Log.e("Error onBackPressed", e)
            }

        } else {
            try {
                fragmentManager.popBackStack()
            } catch (e: Exception) {
                Log.e("Error on popBackStack", e)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)

        overridePendingTransitionForStartActivity()
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)

        overridePendingTransitionForStartActivity()
    }

    fun startActivity(intent: Intent,
                      overridePendingTransition: Boolean) {

        if (overridePendingTransition) {
            startActivity(intent)
            return
        }

        super.startActivity(intent)
    }

    override fun finish() {
        super.finish()

        overridePendingTransitionForFinish()
    }

    override fun overridePendingTransitionForStartActivity() {

        val startEnterAnim = startEnterAnim
        val startExitAnim = startExitAnim
        if (startEnterAnim != null && startExitAnim != null) {
            overridePendingTransition(startEnterAnim, startExitAnim)
        }
    }

    override fun overridePendingTransitionForFinish() {

        val finishEnterAnim = finishEnterAnim
        val finishExitAnim = finishExitAnim
        if (finishEnterAnim != null && finishExitAnim != null) {
            overridePendingTransition(finishEnterAnim, finishExitAnim)
        }
    }

    override fun isDestroyed(): Boolean = viewModel.isActivityDestroyed

    val isViewBound: Boolean
        get() = !viewModel.isActivityPaused || dataBinding != null

    /**
     * @param msgRes The message on loading dialog
     * *
     */
    @Synchronized
    fun showLoadingDialog(@StringRes msgRes: Int? = R.string.loading) {

        loadingDialogMsg = msgRes
        if (msgRes == NONE) {
            Log.w("Showing loading dialog with NONE msg?!")
            dismissLoadingDialog()
            return
        }

        if (viewModel.isActivityPaused) {
            Log.d("Activity is paused.")
            return
        }

        val fragmentManager = supportFragmentManager
        val existedDialog = fragmentManager.findFragmentByTag(DIALOG_TAG_LOADING)
        if (existedDialog != null && !existedDialog.isRemoving) {
            Log.d("Progress dialog already created.")
            if (existedDialog is LoadingDialogFragment) {
                existedDialog.setLoadingMsg(msgRes)
            }
            return
        }

        val loadingDialogFragment = LoadingDialogFragment()
        loadingDialogFragment.setLoadingMsg(msgRes)
        loadingDialogFragment.show(fragmentManager, DIALOG_TAG_LOADING)

        UIUtil.hideSoftKeyboard(this)
    }

    @Synchronized
    fun dismissLoadingDialog() {

        loadingDialogMsg = NONE
        if (viewModel.isActivityPaused) {
            Log.d("Activity is paused.")
            return
        }

        val fragmentManager = supportFragmentManager
        val loadingDialogFragment = fragmentManager.findFragmentByTag(DIALOG_TAG_LOADING)
        if (loadingDialogFragment is LoadingDialogFragment) {
            try {
                loadingDialogFragment.dismiss()
            } catch (e: Exception) {
                Log.e("Error on dismiss loading dialog", e)
            }

        } else if (loadingDialogFragment != null) {
            Log.wtf("Used a wrong tag to attach fragment!")
        }
        // No loading dialog
    }

    @Synchronized
    fun dismissedLoading(): Boolean = loadingDialogMsg == NONE

    /**
     * @param actionBarTitleId The title of this activity. [.NONE] if no title
     */
    fun setActionBarTitle(@StringRes actionBarTitleId: Int) {
        setActionBarTitle(if (actionBarTitleId == NONE) null else getString(actionBarTitleId))
    }

    fun setActionBarTitle(title: String?) {
        actionBarTitle = title

        if (isViewBound) {
            val actionBar = supportActionBar
            if (actionBar != null) {
                val showTitle = actionBarTitle != null
                actionBar.setDisplayShowTitleEnabled(showTitle)
                if (showTitle) {
                    actionBar.title = actionBarTitle
                }
            }
        }
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(@StringRes messageRes: Int,
                     @SnackbarUtil.SnackbarDuration duration: Int) {
        showSnackBar(getString(messageRes), duration)
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(message: String,
                     @SnackbarUtil.SnackbarDuration duration: Int) {

        val coordinatorLayout = coordinatorLayoutRef.get()
        val rootView = coordinatorLayout ?: rootView.get()
        if (rootView == null) {
            Log.e("Cannot get root view for this activity.")
            return
        }

        runOnUiThread {
            dismissLoadingDialog()

            val activity = this@AppHelpfulActivity
            UIUtil.hideSoftKeyboard(activity)

            val snackbar = SnackbarUtil.create(activity, rootView, message, duration)
            snackbar.show()
        }
    }

    fun setHideNavigationBar(hideNavigation: Boolean) {
        fullScreenFlag = if (hideNavigation) {
            fullScreenFlag or FLAG_HIDE_NAVIGATION_BAR
        } else {
            fullScreenFlag and FLAG_HIDE_NAVIGATION_BAR.inv()
        }
    }

    fun setHideStatusBar(hideStatusBar: Boolean) {
        fullScreenFlag = if (hideStatusBar) {
            fullScreenFlag or FLAG_HIDE_STATUS_BAR
        } else {
            fullScreenFlag and FLAG_HIDE_STATUS_BAR.inv()
        }
    }

    protected fun updateActionBarHomeButton() {

        val actionBar = supportActionBar ?: return

        // Enable/Disable back navigation button in tool bar
        val shouldShowBack = menuBackEnabled || supportFragmentManager.backStackEntryCount > 0
        actionBar.setDisplayShowHomeEnabled(shouldShowBack)
        actionBar.setDisplayHomeAsUpEnabled(shouldShowBack)
    }

    internal fun updateFullScreenStatus() {

        if (fullScreenFlag and FLAG_HIDE_STATUS_BAR == FLAG_HIDE_STATUS_BAR && fullScreenFlag and FLAG_HIDE_NAVIGATION_BAR == FLAG_HIDE_NAVIGATION_BAR) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                UIUtil.hideNavigationAndStatusBar(this)
            }
        } else if (fullScreenFlag and FLAG_HIDE_STATUS_BAR == FLAG_HIDE_STATUS_BAR) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                UIUtil.hideStatusBar(this)
            }
        } else if (fullScreenFlag and FLAG_HIDE_NAVIGATION_BAR == FLAG_HIDE_NAVIGATION_BAR) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                UIUtil.hideNavigationBar(this)
            }
        }
    }


    ///////////////////////////////
    // Class and interface
    ///////////////////////////////
    private class MyOnBackStackChangedListener internal constructor(activity: AppHelpfulActivity<*>) : FragmentManager.OnBackStackChangedListener {

        private val activityRef: WeakReference<AppHelpfulActivity<*>> = WeakReference(activity)

        override fun onBackStackChanged() {

            val activity = activityRef.get() ?: return

            activity.updateActionBarHomeButton()
        }
    }

    private class MyOnSystemUiVisibilityChangeListener internal constructor(activity: AppHelpfulActivity<*>) : View.OnSystemUiVisibilityChangeListener {

        internal val activityRef: WeakReference<AppHelpfulActivity<*>> = WeakReference(activity)

        override fun onSystemUiVisibilityChange(visibility: Int) {

            val activity = activityRef.get() ?: return

            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                activity.updateFullScreenStatus()
            }
        }
    }

    companion object {
        val NONE = 0
        val DIALOG_TAG_LOADING = "ProgressDialogFragment"

        private val FLAG_HIDE_NAVIGATION_BAR = 1
        private val FLAG_HIDE_STATUS_BAR = 1 shl 1
    }
}
