package com.example.sharet

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sharet.data.SharedResource


class SharedResourceAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<SharedResource>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.name.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        TODO("Not yet implemented")
    }
}