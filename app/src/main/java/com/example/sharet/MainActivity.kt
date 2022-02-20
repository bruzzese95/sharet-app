package com.example.sharet

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.sharet.databinding.ActivityMainBinding
import com.example.sharet.databinding.CustomDialogAddResourceBinding

class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        activityMainBinding?.addResourceButton?.setOnClickListener {
            showCustomDialog()
        }
    }

    override fun onDestroy() {
        activityMainBinding = null
        super.onDestroy()
    }

    private fun showCustomDialog() {
        val dialogBinding: CustomDialogAddResourceBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.custom_dialog_add_resource,
                null,
                false
            )

        val customDialog = AlertDialog.Builder(this, 0).create()

        customDialog.apply {
            setView(dialogBinding?.root)
            setCancelable(false)
        }.show()

        dialogBinding?.submitCreateResourceButton?.setOnClickListener {
            customDialog.dismiss()
        }
    }
}