package com.sotwtm.support.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.Window
import com.sotwtm.support.R
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.databinding.DialogLoadingBinding
import com.sotwtm.util.Log


/**
 * A general loading dialog.

 * Created by johntsai on 29/7/15.
 * @author John
 */
class LoadingDialogFragment : AppHelpfulDataBindingDialogFragment<DialogLoadingBinding>() {

    init {
        isCancelable = CANCELABLE
    }

    override val layoutId: Int? = R.layout.dialog_loading
    override val dataBinder: AppHelpfulDialogFragmentDataBinder? = null

    @StringRes
    private var loadingMsgRes: Int? = R.string.loading
    private var dismissed: Boolean = false


    fun setLoadingMsg(@StringRes msgRes: Int?) {
        loadingMsgRes = msgRes
        dataBinding?.loadingMsg = msgRes
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window?.let {
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        return dialog
    }

    override fun initDataBinding(dataBinding: DialogLoadingBinding, savedInstanceState: Bundle?) {

        val msgRes = loadingMsgRes
        if (msgRes == AppHelpfulActivity.NONE) {
            Log.e("Showing NONE msg loading dialog?!")
            dismiss()
            return
        }
        dataBinding.loadingMsg = msgRes
    }

    override fun onResume() {
        super.onResume()

        // Sometime dismiss event called before loading dialog ready
        if (dismissed) {
            dismiss()
        }
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        dismissed = false
        super.show(manager, tag)
    }

    override fun show(transaction: FragmentTransaction?, tag: String?): Int {
        dismissed = false
        return super.show(transaction, tag)
    }

    override fun showNow(manager: FragmentManager?, tag: String?) {
        dismissed = false
        super.showNow(manager, tag)
    }

    override fun dismissAllowingStateLoss() {
        dismissed = true
        super.dismissAllowingStateLoss()
    }

    override fun dismiss() {
        dismissed = true
        super.dismiss()
    }

    companion object {
        private const val CANCELABLE = false
    }
}
