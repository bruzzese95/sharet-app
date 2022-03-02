package com.example.sharet.sharedresource

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharet.R
import com.example.sharet.database.SharedResource

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class SharedResourceAdapter : RecyclerView.Adapter<SharedResourceAdapter.ViewHolder>() {

    var data = listOf<SharedResource>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size


    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.sharedResourceName.text = item.resourceName
        holder.resourceImage.setImageResource(R.drawable.ic_baseline_whatshot_24)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item_shared_resource, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sharedResourceName: TextView = itemView.findViewById(R.id.resource_name)
        val resourceImage: ImageView = itemView.findViewById(R.id.resource_image)
    }
}