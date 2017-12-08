package com.sotwtm.support.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
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
class LoadingDialogFragment : AppHelpfulDialogFragment<DialogLoadingBinding>() {

    init {
        isCancelable = CANCELABLE
    }

    override val viewModel: AppHelpfulDialogFragmentViewModel? = null

    @StringRes
    private var loadingMsgRes: Int? = R.string.loading

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val msgRes = loadingMsgRes
        if (msgRes == AppHelpfulActivity.NONE) {
            Log.e("Showing NONE msg loading dialog?!")
            dismiss()
            return
        }
        dataBinding?.loadingMsg = msgRes
    }

    override fun onResume() {
        super.onResume()

        // Sometime dismiss event called before loading dialog ready
        if ((activity as? AppHelpfulActivity<*>)?.dismissedLoading() != false) {
            dismiss()
        }
    }

    companion object {
        private const val CANCELABLE = false
    }
}
