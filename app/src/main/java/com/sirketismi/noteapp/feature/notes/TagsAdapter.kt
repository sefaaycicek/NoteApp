package com.sirketismi.noteapp.feature.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sirketismi.noteapp.databinding.ListItemSingleTagBinding

class TagsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var tagList = listOf<String>()
    fun setList(list : List<String>) {
        this.tagList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder : RecyclerView.ViewHolder? = null
        var binding = ListItemSingleTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewHolder = TagItemViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as TagItemViewHolder
        val item = tagList.get(position)
        viewHolder.bind(item)
    }
}

class TagItemViewHolder(val binding : ListItemSingleTagBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tag : String) {
        binding.txtTagTitle.text = "#$tag"
    }
}