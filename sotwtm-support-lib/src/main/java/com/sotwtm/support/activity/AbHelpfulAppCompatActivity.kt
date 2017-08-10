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

/**
 * Activity applied animation on transit.
 * Created by John on 10/11/2015.

 * @author John
 */
abstract class AbHelpfulAppCompatActivity<DataBindingClass : ViewDataBinding> : AppCompatActivity(), IOverridePendingTransition {

    @IdRes
    private var toolbarId = R.id.toolbar
    @MenuRes
    private var menuResId = NONE

    protected var coordinatorLayoutId: Int = R.id.coordinator_layout
        set(@IdRes value) {
            field = value
        }
        @IdRes get
    protected val coordinatorLayoutRef: WeakReference<CoordinatorLayout?> by lazy {
        WeakReference(
                if (coordinatorLayoutId == NONE)
                    null
                else
                    dataBinding?.root?.findViewById<CoordinatorLayout?>(coordinatorLayoutId)
        )
    }
    protected val rootView: WeakReference<View?> by lazy {
        WeakReference(
                findViewById<View?>(android.R.id.content)
        )
    }

    private var actionBarTitle: String? = null

    @StringRes
    private var loadingDialogMsg: Int? = NONE

    private var backStackListener: MyOnBackStackChangedListener? = null

    @Volatile var dataBinding: DataBindingClass? = null
        private set
    private var fullScreenFlag = 0x0

    /**
     * The layout ID for this activity
     */
    @get:LayoutRes
    abstract protected val layoutResId: Int
    /**
     * Indicate if there should be back button on toolbar
     * */
    open val menuBackEnabled: Boolean = false
    open var viewModel: AbActivityViewModel = object: AbActivityViewModel(){}
        @Synchronized
        set(value) {
            field = value.syncStatus(field)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding?.unbind()
        dataBinding = DataBindingUtil.setContentView<DataBindingClass>(this, layoutResId)

        if (toolbarId != NONE) {
            val toolbar = findViewById<Toolbar?>(toolbarId)
            setSupportActionBar(toolbar)
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

        if (menuResId != NONE) {
            menuInflater.inflate(menuResId, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
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

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)

        overridePendingTransitionForStartActivity()
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)

        overridePendingTransitionForStartActivity()
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

    override fun isDestroyed(): Boolean {
        return viewModel.isActivityDestroyed
    }

    val isViewBound: Boolean
        get() = !viewModel.isActivityPaused || dataBinding != null

    /**
     * @param msgRes The message on loading dialog
     * *
     */
    @JvmOverloads
    @Synchronized fun showLoadingDialog(@StringRes msgRes: Int? = R.string.loading) {

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

    @Synchronized fun dismissLoadingDialog() {

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

    @Synchronized fun dismissedLoading(): Boolean = loadingDialogMsg == NONE

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

            val activity = this@AbHelpfulAppCompatActivity
            UIUtil.hideSoftKeyboard(activity)

            val snackbar = SnackbarUtil.create(activity, rootView, message, duration)
            snackbar.show()
        }
    }

    fun setHideNavigationBar(hideNavigation: Boolean) {
        if (hideNavigation) {
            fullScreenFlag = fullScreenFlag or FLAG_HIDE_NAVIGATION_BAR
        } else {
            fullScreenFlag = fullScreenFlag and FLAG_HIDE_NAVIGATION_BAR.inv()
        }
    }

    fun setHideStatusBar(hideStatusBar: Boolean) {
        if (hideStatusBar) {
            fullScreenFlag = fullScreenFlag or FLAG_HIDE_STATUS_BAR
        } else {
            fullScreenFlag = fullScreenFlag and FLAG_HIDE_STATUS_BAR.inv()
        }
    }

    /**
     * The Enter screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    protected val startEnterAnim: Int?
        @AnimRes
        get() = R.anim.slide_in_from_right

    /**
     * The Exit screen animation to override on start activity
     * @return `null` means not override the activity animation
     * *
     */
    protected val startExitAnim: Int?
        @AnimRes
        get() = R.anim.slide_out_to_left

    /**
     * The Enter screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    protected val finishEnterAnim: Int?
        @AnimRes
        get() = R.anim.slide_in_from_left

    /**
     * The Exit screen animation to override on finish activity
     * @return `null` means not override the activity animation
     * *
     */
    protected val finishExitAnim: Int?
        @AnimRes
        get() = R.anim.slide_out_to_right

    /**
     * @param toolbarId The toolbar view ID in this layout. Set as [.NONE] if no toolbar in this activity
     * *
     */
    protected fun setToolbarId(@IdRes toolbarId: Int) {
        this.toolbarId = toolbarId
    }

    /**
     * @param menuResId Menu ID for this activity. Set as [.NONE] if no menu
     * *
     */
    protected fun setMenuResId(@MenuRes menuResId: Int) {
        this.menuResId = menuResId
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
    private class MyOnBackStackChangedListener internal constructor(activity: AbHelpfulAppCompatActivity<*>) : FragmentManager.OnBackStackChangedListener {

        private val activityRef: WeakReference<AbHelpfulAppCompatActivity<*>> = WeakReference(activity)

        override fun onBackStackChanged() {

            val activity = activityRef.get() ?: return

            activity.updateActionBarHomeButton()
        }
    }

    private class MyOnSystemUiVisibilityChangeListener internal constructor(activity: AbHelpfulAppCompatActivity<*>) : View.OnSystemUiVisibilityChangeListener {

        internal val activityRef: WeakReference<AbHelpfulAppCompatActivity<*>> = WeakReference(activity)

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
