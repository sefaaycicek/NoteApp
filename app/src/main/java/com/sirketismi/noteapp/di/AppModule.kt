package com.sirketismi.noteapp.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.sirketismi.noteapp.dao.Appdatabase
import com.sirketismi.noteapp.dao.Migration2to3
import com.sirketismi.noteapp.dao.Migration3to4
import com.sirketismi.noteapp.dao.Migration4to5
import com.sirketismi.noteapp.dao.Migration5to6
import com.sirketismi.noteapp.dao.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDao(appDatabase : Appdatabase) : NotesDao {
        return appDatabase.noteDao()
    }

    @Singleton
    @Provides
    fun provideFirestore() : FirebaseFirestore {
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun provideAppdatabase(@ApplicationContext appContext: Context) : Appdatabase {
        val instance = Room.databaseBuilder(
            appContext.applicationContext,
            Appdatabase::class.java,
            "notedb"
        ).addMigrations(Migration2to3(), Migration3to4(), Migration4to5(), Migration5to6()).build()
        return instance
    }
}