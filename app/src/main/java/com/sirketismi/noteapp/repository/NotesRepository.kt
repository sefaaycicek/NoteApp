package com.sirketismi.noteapp.repository

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import com.sirketismi.noteapp.dao.NotesDao
import com.sirketismi.noteapp.model.NoteEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface NoteRepositoryInterface {
    suspend fun insert(note : NoteEntity)
    fun getAll() : LiveData<List<NoteEntity>>
}

class NotesRepository @Inject constructor(private val noteDao : NotesDao)  : NoteRepositoryInterface {
    override suspend fun insert(note: NoteEntity) {
        noteDao.insert(note)
    }

    override fun getAll(): LiveData<List<NoteEntity>> {
        return noteDao.getAll()
    }

    suspend fun getAllFlow() = callbackFlow<Resource<List<NoteEntity>>> {
        noteDao.getAll()
        noteDao.getAllFlow().collect {
            trySend(Resource.Success(it))
        }
        awaitClose { }
    }
}
