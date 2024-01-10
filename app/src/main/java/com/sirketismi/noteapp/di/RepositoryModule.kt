package com.sirketismi.noteapp.di

import com.sirketismi.noteapp.repository.FirebaseRepository
import com.sirketismi.noteapp.repository.FirebaseRepositoryInterface
import com.sirketismi.noteapp.repository.NoteRepositoryInterface
import com.sirketismi.noteapp.repository.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindNoteRepository(im : NotesRepository) : NoteRepositoryInterface

    @Binds
    @Singleton
    abstract fun bindFirebaseRepository(im : FirebaseRepository) : FirebaseRepositoryInterface
}