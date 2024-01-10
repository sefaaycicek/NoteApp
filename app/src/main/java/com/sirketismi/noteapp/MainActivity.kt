package com.sirketismi.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore

/*var note = hashMapOf(
   "name-1" to "123123",
   "title-1" to "21321",
   "detail-1" to "dasdasd23"
   )

val noteCollection = db.collection("NoteApp")
noteCollection.addSnapshotListener { value, error ->

}



   noteCollection.add(note)
   .addOnSuccessListener {

   }
   .addOnFailureListener {

   }

noteCollection
   .get()
   .addOnSuccessListener {

   }
   .addOnFailureListener {

   }*/

}
}