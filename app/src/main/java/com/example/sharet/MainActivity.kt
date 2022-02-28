package com.example.sharet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.sharet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    /*private fun showCustomDialog() {
        val dialogBinding: CustomDialogAddResourceBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.custom_dialog_add_resource,
                null,
                false
            )

        val customDialog = AlertDialog.Builder(this, 0).create()
        val nameRes = dialogBinding?.nameResource

        customDialog.apply {
            setView(dialogBinding?.root)
            setCancelable(false)
        }.show()

        dialogBinding?.submitCreateResourceButton?.setOnClickListener {
            resources.add(SharedResource(nameRes?.text.toString()))
            adapter.notifyDataSetChanged()
            customDialog.dismiss()
        }
    }*/
}