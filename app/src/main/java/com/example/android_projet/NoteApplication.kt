package com.example.android_projet

import android.app.Application
import com.example.android_projet.data.database.NoteDatabase
import com.example.android_projet.data.repository.NoteRepository

class NoteApplication : Application() {
    private val database by lazy { NoteDatabase.getDatabase(this) }
    val repository: NoteRepository by lazy { NoteRepository(database.noteDao()) }
}
