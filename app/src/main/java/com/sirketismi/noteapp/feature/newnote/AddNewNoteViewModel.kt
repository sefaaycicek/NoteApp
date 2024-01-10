package com.sirketismi.noteapp.feature.newnote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirketismi.noteapp.model.NoteEntity
import com.sirketismi.noteapp.repository.FirebaseRepository
import com.sirketismi.noteapp.repository.NotesRepository
import com.sirketismi.noteapp.util.format
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddNewNoteViewModel @Inject constructor(val repository: NotesRepository, val firebaseRepository : FirebaseRepository): ViewModel() {
    var noteTitle = MutableLiveData<String>()
    var noteDetail = MutableLiveData<String>()
    var noteTag = MutableLiveData<String>()
    var noteDateText = MutableLiveData<String>()
    var noteDateValue : Date = Date()

    var onSaveCompleted = MutableLiveData<Boolean>(false)
    var onShowDatePickerTapped = MutableLiveData<Boolean>(false)

    fun onAddButtonClick() {
        if(isValid()) {
            insertNote()
            onSaveCompleted.postValue(true)
        }
    }

    fun onShowDatePicker() {
        onShowDatePickerTapped.postValue(true)
    }

    fun userSelectDate(date : Long) {
        noteDateValue = Date(date)
        noteDateText.postValue(noteDateValue.format())
    }
    private fun isValid() : Boolean {
        return !(noteTitle.value.isNullOrEmpty() || noteDetail.value.isNullOrEmpty())
    }

    private fun insertNote() {
        val note = NoteEntity(
            title = noteTitle.value,
            detail = noteDetail.value,
            date = noteDateValue.time,
            tag = noteTag.value ?: "")

        viewModelScope.launch {
            repository.insert(note)
        }

        firebaseRepository.addNote(note) {

        }
    }
}