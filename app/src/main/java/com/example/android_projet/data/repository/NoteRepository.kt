package com.example.android_projet.data.repository

import com.example.android_projet.data.dao.NoteDao
import com.example.android_projet.data.model.ModuleCount
import com.example.android_projet.data.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    fun getNotesByModule(module: String): Flow<List<Note>> = noteDao.getNotesByModule(module)

    fun getFavoriteNotes(): Flow<List<Note>> = noteDao.getFavoriteNotes()

    fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)

    suspend fun getNoteById(id: Long): Note? = noteDao.getNoteById(id)

    suspend fun insertNote(note: Note): Long = noteDao.insertNote(note)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    fun getModules(): Flow<List<String>> = noteDao.getModules()

    fun getModuleNoteCounts(): Flow<List<ModuleCount>> = noteDao.getModuleNoteCounts()
}
