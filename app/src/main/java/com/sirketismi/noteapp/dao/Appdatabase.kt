package com.sirketismi.noteapp.dao

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.sirketismi.noteapp.model.NoteEntity
import com.sirketismi.noteapp.model.User
//
@Database(entities = [NoteEntity::class, User::class], version = 6,
    autoMigrations = [AutoMigration(1, 2, spec = Appdatabase.Migration1To2::class)])
abstract class Appdatabase : RoomDatabase() {
    abstract fun noteDao() : NotesDao

    @RenameColumn(
        tableName = "NoteEntity",
        fromColumnName = "detail",
        toColumnName = "note_detail"
    )
    class Migration1To2 : AutoMigrationSpec

}