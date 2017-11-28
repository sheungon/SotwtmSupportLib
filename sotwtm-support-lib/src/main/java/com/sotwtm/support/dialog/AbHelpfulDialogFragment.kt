package com.sotwtm.support.dialog

import android.content.Context
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.annotation.UiThread
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import com.sotwtm.support.R
import com.sotwtm.support.activity.AbHelpfulAppCompatActivity
import java.lang.ref.WeakReference

/**
 */
abstract class AbHelpfulDialogFragment<DataBindingClass : ViewDataBinding> : DialogFragment() {

    @Volatile var dataBinding: DataBindingClass? = null
        private set

    override fun onDestroy() {
        super.onDestroy()

        dataBinding?.unbind()
        dataBinding = null
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

    protected fun showLoading() {
        (activity as? AbHelpfulAppCompatActivity<*>)?.showLoadingDialog(null)
    }

    protected fun dismissLoading() {
        (activity as? AbHelpfulAppCompatActivity<*>)?.dismissLoadingDialog()
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
    protected class OnShowSetupOnOkClickListener(fragment: AbHelpfulDialogFragment<*>) : DialogInterface.OnShowListener {

        internal val fragmentRef: WeakReference<AbHelpfulDialogFragment<*>> = WeakReference(fragment)

        override fun onShow(dialog: DialogInterface) {

            val fragment = fragmentRef.get() ?: return

            /*Set listener here to avoid dialog auto dismissed*/
            val button = (dialog as? AlertDialog)?.getButton(AlertDialog.BUTTON_POSITIVE)
            button?.setOnClickListener(MyOnOkClickListener(fragment))
        }
    }

    internal class MyOnOkClickListener(fragment: AbHelpfulDialogFragment<*>) : View.OnClickListener {

        val fragmentRef: WeakReference<AbHelpfulDialogFragment<*>> = WeakReference(fragment)

        override fun onClick(view: View) {

            val fragment = fragmentRef.get() ?: return

            fragment.onOkClick()
        }
    }
}
