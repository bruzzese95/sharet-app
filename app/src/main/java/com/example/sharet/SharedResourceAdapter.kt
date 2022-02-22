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
class SharedResourceAdapter: RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<SharedResource>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_shared_resource, parent, false) as TextView
        return TextItemViewHolder(view)
    }


    // Returns the total count of items in the list
    override fun getItemCount() = data.size
}

