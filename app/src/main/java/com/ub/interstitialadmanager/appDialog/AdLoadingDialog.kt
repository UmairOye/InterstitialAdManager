package com.ub.interstitialadmanager.appDialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.ub.interstitialadmanager.R
import com.ub.interstitialadmanager.databinding.LoadingDialogBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdLoadingDialog {

    fun showLoadingDialog(context: Context, durationMs: Long, action:()-> Unit): Dialog {
        val binding = LoadingDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(context).apply {
            setContentView(binding.root)
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        dialog.show()

        /*
        Q: why are we using lifecycleScope here?
        Ans: If the Handler is tied to an object with a long lifecycle (e.g., an activity or fragment),
         it can prevent that object from being garbage collected, causing a memory leak
         so we are using a CoroutineScope to dismiss the dialog after a delay
         */
        (context as? LifecycleOwner)?.lifecycleScope?.launch {
            delay(durationMs)
            if (dialog.isShowing) {
                action.invoke()
                dialog.dismiss()
            }
        }

        return dialog
    }



}