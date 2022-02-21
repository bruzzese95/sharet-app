package com.example.sharet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharet.data.SharedResource


// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class SharedResourceAdapter (private val resources: List<SharedResource>) : RecyclerView.Adapter<SharedResourceAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView = itemView.findViewById<TextView>(R.id.resource_name)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharedResourceAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_shared_resource, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: SharedResourceAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val resource: SharedResource = resources.get(position)
        // Set item views based on your views and data model
        val textView = viewHolder.nameTextView
        textView.setText(resource.name)
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return resources.size
    }
}