package com.example.sharet.customdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.sharet.R
import com.example.sharet.databinding.CustomDialogAddResourceBinding

class CustomDialogFragment: DialogFragment() {

    private lateinit var customDialogViewModel: CustomDialogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: CustomDialogAddResourceBinding = DataBindingUtil.inflate(
            inflater, R.layout.custom_dialog_add_resource, container, false)


        customDialogViewModel = ViewModelProviders.of(this).get(CustomDialogViewModel::class.java)

        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}