package com.example.sharet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sharet.data.SharedResource
import com.example.sharet.databinding.ActivityMainBinding
import com.example.sharet.databinding.CustomDialogAddResourceBinding

class MainActivity : AppCompatActivity() {
/*
    lateinit var resources: ArrayList<SharedResource>
    lateinit var adapter: SharedResourceAdapter*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        /*binding?.addResourceButton?.setOnClickListener {
            showCustomDialog()
        }

        // Lookup the recyclerview in activity layout
        val rvResources = findViewById<View>(R.id.rvSharedResource) as RecyclerView
        // Initialize contacts
        resources = SharedResource.createResourcesList("")
        // Create adapter passing in the sample user data
        adapter = SharedResourceAdapter(resources)
        // Attach the adapter to the recyclerview to populate items
        rvResources.adapter = adapter
        // Set layout manager to position the items
        rvResources.layoutManager = LinearLayoutManager(this)
        // That's all!*/

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