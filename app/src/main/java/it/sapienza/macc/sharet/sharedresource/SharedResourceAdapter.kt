package it.sapienza.macc.sharet.sharedresource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.sapienza.macc.sharet.database.SharedResource
import it.sapienza.macc.sharet.databinding.ListItemSharedResourceBinding


// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class SharedResourceAdapter(val clickListenerResource: SharedResourceListener, val clickListenerDelete: DeleteResourceListener, val clickListenerAddUser: AddUserListener) : ListAdapter<SharedResource,
        SharedResourceAdapter.ViewHolder>(SharedResourceDiffCallback()) {


    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListenerResource, clickListenerDelete, clickListenerAddUser, item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor (val binding: ListItemSharedResourceBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListenerResource: SharedResourceListener, clickListenerDelete: DeleteResourceListener, clickListenerAddUser: AddUserListener, item: SharedResource) {
            binding.sharedResource = item
            binding.clickListenerResource = clickListenerResource
            binding.clickListenerDelete = clickListenerDelete
            binding.clickListenerAddUser = clickListenerAddUser
            binding.executePendingBindings()
        }


    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemSharedResourceBinding.inflate(layoutInflater, parent, false)

            return ViewHolder(binding)
        }
    }
}


}

class SharedResourceDiffCallback : DiffUtil.ItemCallback<SharedResource>() {
    override fun areItemsTheSame(oldItem: SharedResource, newItem: SharedResource): Boolean {
        return oldItem.resourceId == newItem.resourceId
    }

    override fun areContentsTheSame(oldItem: SharedResource, newItem: SharedResource): Boolean {
        return oldItem == newItem
    }
}




class SharedResourceListener(val clickListener: (resourceId: Long) -> Unit) {
    fun onClick(resource: SharedResource) = clickListener(resource.resourceId)
}

class DeleteResourceListener(val clickListener: (resourceId: Long) -> Unit) {
    fun onClick(resource: SharedResource) = clickListener(resource.resourceId)
}

class AddUserListener(val clickListener: () -> Unit) {
    fun onClick() = clickListener()
}