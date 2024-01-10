package com.sirketismi.noteapp.dao

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration2to3 : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE 'NoteEntity' ADD COLUMN 'noteDate' INTEGER NOT NULL")
    }
}

class Migration3to4 : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `User` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `surname` TEXT)")
    }
}

class Migration4to5 : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE 'User' ADD COLUMN 'age' INTEGER NOT NULL")
    }
}

class Migration5to6 : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE 'NoteEntity' ADD COLUMN 'tag' TEXT NOT NULL DEFAULT ''")
    }
}