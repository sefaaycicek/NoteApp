package com.sirketismi.noteapp.feature.notes

import android.content.ClipData.Item
import android.content.Context
import android.preference.PreferenceActivity.Header
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.sirketismi.noteapp.databinding.ListItemHeaderBinding
import com.sirketismi.noteapp.databinding.ListItemNoteBinding
import com.sirketismi.noteapp.databinding.ListItemTagsBinding
import com.sirketismi.noteapp.model.HeaderWrapper
import com.sirketismi.noteapp.model.NoteEntity
import com.sirketismi.noteapp.model.TagsWrapper
import com.sirketismi.noteapp.util.format
import java.util.Date

class NoteListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val HEADER = 1
    val ITEM = 2
    val TAGS = 3

    var newList = mutableListOf<Any>()
    var noteList = listOf<NoteEntity>()

    fun setList(list : List<NoteEntity>) {
        this.noteList = list

        newList.clear()
        val groupedList = noteList.groupBy { it.date }
        val tagList = noteList.map { it.tag }.filter { !it.isNullOrEmpty() }.distinct()
        val tagsWrapper = TagsWrapper(tagList)

        if(tagList.isNotEmpty()) {
            newList.add(tagsWrapper)
        }

        groupedList.forEach {key, value ->
            newList.add(HeaderWrapper(key))

            newList.addAll(value)

            if(tagList.isNotEmpty()) {
                newList.add(tagsWrapper)
            }

        }


        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val item = newList.get(position)
        when(item) {
            is TagsWrapper -> return TAGS
            is HeaderWrapper -> return HEADER
            else -> return ITEM
        }
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder : RecyclerView.ViewHolder? = null

        when(viewType) {
            HEADER -> {
                var binding = ListItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = HeaderViewHolder(binding)
            }

            TAGS -> {
                var binding = ListItemTagsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = TagsViewHolder(binding)
            }

            ITEM -> {
                var binding = ListItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = NoteViewHolder(binding)
            }
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType)  {
            HEADER -> {
                val viewHolder = holder as HeaderViewHolder
                val item = newList.get(position) as HeaderWrapper
                val date = Date(item.time)
                viewHolder.bind(date.format())
            }

            ITEM-> {
                val viewHolder = holder as NoteViewHolder
                val item = newList.get(position) as NoteEntity
                viewHolder.bind(item)
            }

            TAGS-> {
                val viewHolder = holder as TagsViewHolder
                val item = newList.get(position) as TagsWrapper
                viewHolder.bind(item)
            }
        }
    }
}

class NoteViewHolder(val binding : ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(note : NoteEntity) {
        binding.txtNote.text = note.title
        binding.txtNoteDetail.text = note.detail
    }
}

class HeaderViewHolder(val binding : ListItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(title : String) {
        binding.txtNote.text = title
    }
}

class TagsViewHolder(val binding : ListItemTagsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tagItem : TagsWrapper) {
        val layoutManager = FlexboxLayoutManager(binding.root.context)
        layoutManager.flexWrap = FlexWrap.WRAP

        binding.recylerView.layoutManager = layoutManager

        val adapter = TagsAdapter()
        binding.recylerView.adapter = adapter

        adapter.setList(tagItem.tags)
    }
}