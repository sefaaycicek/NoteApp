package com.sirketismi.noteapp.repository

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sirketismi.noteapp.model.NoteEntity
import javax.inject.Inject

interface FirebaseRepositoryInterface {
    fun getNoteList(result : (List<NoteEntity>)->Unit)
    fun addNote(noteEntity: NoteEntity, result : (Boolean)->Unit)
    fun deleteNote(documentId : String, result : ()->Unit)
}
class FirebaseRepository @Inject constructor(val database : FirebaseFirestore) : FirebaseRepositoryInterface {
    override fun getNoteList(result: (List<NoteEntity>) -> Unit) {
        database.collection("NoteEntity")
            //.orderBy("title", Query.Direction.ASCENDING).limit(10)
            //.whereIn("tag", listOf("tag-1", "odev"))
            //.whereEqualTo("title" , "")
            //.whereEqualTo("user.name" , "Sefa")
            //.whereArrayContains("detaylar", "firebase")
            //.where(Filter.or(Filter.equalTo("title", "1"), Filter.equalTo("title", "2")))
            .get()
            .addOnSuccessListener {
                val tempList = mutableListOf<NoteEntity>()
                for(document in it) {
                    val noteEntity = document.toObject(NoteEntity::class.java)
                    tempList.add(noteEntity)
                    result(tempList)
                }
            }
            .addOnFailureListener {
                result(emptyList())
            }
    }

    override fun addNote(noteEntity: NoteEntity, result: (Boolean) -> Unit) {
        database.collection("NoteEntity")
            .add(noteEntity)
            .addOnSuccessListener {
               result(true)
            }
            .addOnFailureListener {
                result(false)
            }
    }

    override fun deleteNote(documentId: String, result: () -> Unit) {
    }
}