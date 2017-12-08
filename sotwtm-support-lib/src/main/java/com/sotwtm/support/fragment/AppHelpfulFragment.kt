package com.sotwtm.support.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.activity.IOverridePendingTransition
import com.sotwtm.support.util.SnackbarUtil
import com.sotwtm.support.util.UIUtil
import com.sotwtm.util.Log

/**
 * A fragment contains helpful API.

 * Created by sheun on 10/11/2015.
 * @author John
 */
abstract class AppHelpfulFragment<DataBindingClass : ViewDataBinding> : Fragment() {

    @Volatile
    var dataBinding: DataBindingClass? = null
        private set
    abstract val viewModel: AppHelpfulFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onCreate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        dataBinding?.unbind()
        dataBinding = DataBindingUtil.inflate<DataBindingClass>(inflater, viewModel.layoutResId, container, false)

        return dataBinding?.root ?: inflater.inflate(viewModel.layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onViewCreatedInternal(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        viewModel.onStart()
    }

    override fun onResume() {
        super.onResume()

        viewModel.onResumeInternal()
    }

    override fun onPause() {
        super.onPause()

        viewModel.onPauseInternal()
    }

    override fun onStop() {
        super.onStop()

        viewModel.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dataBinding?.unbind()
        dataBinding = null

        viewModel.onDestroyViewInternal()
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.onDestroy()
    }

    override fun startActivity(intent: Intent) {

        val activity = activity
        if (activity == null) {
            Log.e("Cannot start activity as the fragment has been released.")
            return
        }

        super.startActivity(intent)

        if (activity is IOverridePendingTransition) {
            activity.overridePendingTransitionForStartActivity()
        }
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {

        val activity = activity
        if (activity == null) {
            Log.e("Cannot start activity as the fragment has been released.")
            return
        }

        super.startActivityForResult(intent, requestCode)

        if (activity is IOverridePendingTransition) {
            activity.overridePendingTransitionForStartActivity()
        }
    }

    /**
     * @return `true` if view is bound by ButterKnife. Otherwise, `false`
     * *
     */
    val isViewBound: Boolean
        get() = isResumed || dataBinding != null

    fun showLoadingDialog() {

        val activity = activity
        if (activity != null) {
            (activity as? AppHelpfulActivity<*>)?.showLoadingDialog() ?: Log.wtf("This method can only work with parent is AppHelpfulActivity")
        } else {
            Log.v("Fragment released")
        }
    }

    fun showLoadingDialog(@StringRes msgRes: Int?) {

        val activity = activity
        if (activity != null) {
            (activity as? AppHelpfulActivity<*>)?.showLoadingDialog(msgRes) ?: Log.wtf("This method can only work with parent is AppHelpfulActivity")
        } else {
            Log.v("Fragment released")
        }
    }

    fun dismissLoadingDialog() {

        val activity = activity
        if (activity != null) {
            (activity as? AppHelpfulActivity<*>)?.dismissLoadingDialog() ?: Log.wtf("This method can only work with parent is AppHelpfulActivity")
        } else {
            Log.v("Fragment released")
        }
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(@StringRes messageRes: Int,
                     @SnackbarUtil.SnackbarDuration duration: Int) {

        val activity = activity
        (activity as? AppHelpfulActivity<*>)?.showSnackBar(messageRes, duration) ?: if (activity != null) {
            showSnackBar(activity.getString(messageRes), duration)
        } else {
            Log.e("Fragment is not attached! message lost : " + messageRes)
        }
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    fun showSnackBar(message: String,
                     @SnackbarUtil.SnackbarDuration duration: Int) {

        if (!isViewBound) {
            return
        }

        val activity = activity
        (activity as? AppHelpfulActivity<*>)?.showSnackBar(message, duration) ?: if (activity != null) {

            val rootView = view
            if (rootView == null) {
                Log.w("Cannot get root view.")
                return
            }
            activity.runOnUiThread {
                dismissLoadingDialog()
                UIUtil.hideSoftKeyboard(activity)

                val snackbar = SnackbarUtil.create(activity, rootView, message, duration)
                snackbar.show()
            }

        } else {
            Log.e("Fragment is not attached! message lost : " + message)
        }
    }
}
