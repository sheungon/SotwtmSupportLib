package com.sotwtm.support.dialog

import android.content.Context
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.annotation.UiThread
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.sotwtm.support.R
import com.sotwtm.support.activity.AppHelpfulActivity
import java.lang.ref.WeakReference

/**
 */
abstract class AppHelpfulDialogFragment<DataBindingClass : ViewDataBinding> : DialogFragment() {

    abstract val layoutId: Int?
    @Volatile
    var dataBinding: DataBindingClass? = null
        private set
    abstract val viewModel: AppHelpfulDialogFragmentViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel?.onCreate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            layoutId?.let {
                dataBinding?.unbind()
                dataBinding = DataBindingUtil.inflate(inflater, it, container, false)

                dataBinding?.root ?: inflater.inflate(it, container, false)
            } ?: super.onCreateView(inflater, container, savedInstanceState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.onViewCreatedInternal(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        viewModel?.onStart()
    }

    override fun onResume() {
        super.onResume()

        viewModel?.onResumeInternal()
    }

    override fun onPause() {
        super.onPause()

        viewModel?.onPauseInternal()
    }

    override fun onStop() {
        super.onStop()

        viewModel?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel?.onDestroyViewInternal()
    }

    override fun onDestroy() {
        super.onDestroy()

        dataBinding?.unbind()
        dataBinding = null

        viewModel?.onDestroy()
    }

    fun onEditorAction(actionId: Int): Boolean {

        when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                onOkClick()
                return true
            }
        }

        return false
    }

    protected fun createContentView(context: Context,
                                    @LayoutRes layoutId: Int): View? {

        val inflater = LayoutInflater.from(context)
        dataBinding = DataBindingUtil.inflate<DataBindingClass>(inflater, layoutId, null, false)
        return dataBinding?.root
    }

    internal fun showLoadingDialog() {
        showLoadingDialog(null)
    }

    internal fun showLoadingDialog(@StringRes msgRes: Int?) {
        (activity as? AppHelpfulActivity<*>)?.showLoadingDialog(msgRes)
    }

    internal fun dismissLoadingDialog() {
        (activity as? AppHelpfulActivity<*>)?.dismissLoadingDialog()
    }

    protected fun showNothing() {
        setContentView(R.layout.empty)
    }

    /**
     * Override this method to do your action on "OK" clicked if you want to keep this dialog showing.<br></br>
     * You will also need to set [OnShowSetupOnOkClickListener] to your dialog on show.<br></br>
     * !!!!!Note, using this method, you shouldn't implement positive button's onclick listener!!!!!<br></br>
     */
    @UiThread
    protected open fun onOkClick() {
    }

    internal fun setContentView(@LayoutRes layoutResId: Int) {

        val fragmentDialog = dialog ?: return

        // Make dialog content gone
        fragmentDialog.setContentView(LayoutInflater.from(context).inflate(layoutResId, null, false))
        fragmentDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }


    ///////////////////////////////////
    // Class and interface
    ///////////////////////////////////
    /**
     * Set this on show listener to you dialog to make dialog OK button on click response to [.onOkClick].
     * Using this, the dialog will NOT be dismissed on OK button click
     */
    protected class OnShowSetupOnOkClickListener(fragment: AppHelpfulDialogFragment<*>) : DialogInterface.OnShowListener {

        internal val fragmentRef: WeakReference<AppHelpfulDialogFragment<*>> = WeakReference(fragment)

        override fun onShow(dialog: DialogInterface) {

            val fragment = fragmentRef.get() ?: return

            /*Set listener here to avoid dialog auto dismissed*/
            val button = (dialog as? AlertDialog)?.getButton(AlertDialog.BUTTON_POSITIVE)
            button?.setOnClickListener(MyOnOkClickListener(fragment))
        }
    }

    internal class MyOnOkClickListener(fragment: AppHelpfulDialogFragment<*>) : View.OnClickListener {

        val fragmentRef: WeakReference<AppHelpfulDialogFragment<*>> = WeakReference(fragment)

        override fun onClick(view: View) {

            val fragment = fragmentRef.get() ?: return

            fragment.onOkClick()
        }
    }
}
