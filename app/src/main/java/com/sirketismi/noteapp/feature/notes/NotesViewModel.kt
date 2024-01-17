package com.sirketismi.noteapp.feature.notes

import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.sirketismi.noteapp.R
import com.sirketismi.noteapp.model.NoteEntity
import com.sirketismi.noteapp.repository.FirebaseRepository
import com.sirketismi.noteapp.repository.NotesRepository
import com.sirketismi.noteapp.repository.Resource
import com.sirketismi.noteapp.repository.ResourceRepository
import com.sirketismi.noteapp.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,
    val firebaseRepository: FirebaseRepository,
    val resourceRepository: ResourceRepository) : ViewModel() {

    val refreshList = SingleLiveEvent<Boolean>()
    var responseList = listOf<NoteEntity>()


    fun getAllData(): LiveData<List<NoteEntity>> {
        viewModelScope.launch {

        }

        CoroutineScope(Dispatchers.Default).launch {

        }

        GlobalScope.launch {

        }

        return repository.getAll()
    }

    fun getAll() {
        firebaseRepository.getNoteList {
            responseList = it
            refreshList.postValue(true)
        }
    }

    val fetchVersionCode = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        repository.getAllFlow().collect {
            emit(it)
        }

        firebaseRepository.getNoteList().collect {
            emit(it)
        }
    }
}