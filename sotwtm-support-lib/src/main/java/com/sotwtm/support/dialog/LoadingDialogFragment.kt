package com.sotwtm.support.dialog

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.sotwtm.support.R
import com.sotwtm.support.activity.AbHelpfulAppCompatActivity
import com.sotwtm.support.databinding.DialogLoadingBinding
import com.sotwtm.util.Log


/**
 * A general loading dialog.

 * Created by johntsai on 29/7/15.
 * @author John
 */
class LoadingDialogFragment : DialogFragment() {

    @StringRes
    private var loadingMsgRes: Int? = R.string.loading
    private var dataBinding: DialogLoadingBinding? = null

    init {
        isCancelable = CANCELABLE
    }

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layoutId = R.layout.dialog_loading

        dataBinding?.unbind()
        dataBinding = DataBindingUtil.inflate<DialogLoadingBinding>(inflater, layoutId, container, false)

        return dataBinding?.root ?: inflater?.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val msgRes = loadingMsgRes
        if (msgRes == AbHelpfulAppCompatActivity.NONE) {
            Log.e("Showing NONE msg loading dialog?!")
            dismiss()
            return
        }
        dataBinding?.loadingMsg = msgRes
    }

    override fun onResume() {
        super.onResume()

        // Sometime dismiss event called before loading dialog ready
        if ((activity as? AbHelpfulAppCompatActivity<*>)?.dismissedLoading() ?: true) {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dataBinding?.unbind()
        dataBinding = null
    }

    companion object {
        private const val CANCELABLE = false
    }
}
