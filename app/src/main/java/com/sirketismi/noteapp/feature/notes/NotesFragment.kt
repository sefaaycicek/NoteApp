package com.sirketismi.noteapp.feature.notes

import android.os.Bundle
import android.util.LayoutDirection
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.sirketismi.noteapp.R
import com.sirketismi.noteapp.base.BaseFragment
import com.sirketismi.noteapp.databinding.FragmentNotesBinding
import com.sirketismi.noteapp.model.NoteEntity
import com.sirketismi.noteapp.model.TagsWrapper
import com.sirketismi.noteapp.repository.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : BaseFragment() {

    val adapter = TagsAdapter()
    lateinit var noteListAdapter : NoteListAdapter

    lateinit var binding : FragmentNotesBinding
    val viewModel : NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       /* viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.map {
                it * it
            }.collect {
                print("$it")
            }
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater)

        binding.btnSave.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesToNewnote()
            findNavController().navigate(action)

            FirebaseAuth.getInstance().signOut()
            //findNavController().navigate(R.id.loginFragment)

        }

        prepareRecyclerView()
        prepareTagREcyler()
        observeLiveData()


        //viewModel.getAll()

        return binding.root
    }

    private fun observeLiveData() {
        /*viewModel.getAllData().observe(viewLifecycleOwner) {
            refreshList(it)
        }

        viewModel.refreshList.observe(viewLifecycleOwner) {
            refreshList(viewModel.responseList)
        }*/

        viewModel.fetchVersionCode.observe(viewLifecycleOwner) { result->
            when(result){
                is Resource.Loading -> {
//                    showProgress()
                }

                is Resource.Success -> {
                    refreshList(result.data)
                }

                is Resource.Failure -> {

                }
            }
        }
    }

    fun refreshList(list : List<NoteEntity>) {
        noteListAdapter.setList(list)

        val tagList = list.map { it.tag }.filter { !it.isNullOrEmpty() }.distinct()
        val tagsWrapper = TagsWrapper(tagList)
        adapter.setList(tagsWrapper.tags)
    }

    private fun prepareRecyclerView() {
        context?.let {
           // val layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            //val layoutManager = GridLayoutManager(it, 3)
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexWrap = FlexWrap.WRAP
            binding.recylerView.layoutManager = layoutManager
        }

        noteListAdapter = NoteListAdapter()
        binding.recylerView.adapter = noteListAdapter
    }

    private fun prepareTagREcyler() {
        val layoutManager = FlexboxLayoutManager(binding.root.context)
        layoutManager.flexWrap = FlexWrap.WRAP

        binding.recylerViewTags.layoutManager = layoutManager

        binding.recylerViewTags.adapter = adapter


    }
}