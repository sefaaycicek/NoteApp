package com.sirketismi.noteapp.feature.notes

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sirketismi.noteapp.R
import com.sirketismi.noteapp.model.NoteEntity
import com.sirketismi.noteapp.repository.FirebaseRepository
import com.sirketismi.noteapp.repository.NotesRepository
import com.sirketismi.noteapp.repository.ResourceRepository
import com.sirketismi.noteapp.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,
    val firebaseRepository: FirebaseRepository,
    val resourceRepository: ResourceRepository) : ViewModel() {

    val refreshList = SingleLiveEvent<Boolean>()
    var responseList = listOf<NoteEntity>()

    fun getAllData(): LiveData<List<NoteEntity>> {
        return repository.getAll()
    }

    fun getAll() {
        firebaseRepository.getNoteList {
            responseList = it
            refreshList.postValue(true)
        }
    }
}