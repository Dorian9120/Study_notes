package com.example.android_projet.data.dao

import androidx.room.*
import com.example.android_projet.data.model.ModuleCount
import com.example.android_projet.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE module = :module ORDER BY updatedAt DESC")
    fun getNotesByModule(module: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    fun getFavoriteNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchNotes(query: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Long): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT DISTINCT module FROM notes ORDER BY module ASC")
    fun getModules(): Flow<List<String>>

    @Query("SELECT module, COUNT(*) AS count FROM notes GROUP BY module ORDER BY module ASC")
    fun getModuleNoteCounts(): Flow<List<ModuleCount>>
}
