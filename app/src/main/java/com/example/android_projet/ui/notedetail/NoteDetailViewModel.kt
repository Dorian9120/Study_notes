package com.example.android_projet.ui.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_projet.data.model.Note
import com.example.android_projet.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteDetailViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _note = MutableLiveData<Note?>()
    val note: LiveData<Note?> = _note

    private val _navigateBack = MutableLiveData(false)
    val navigateBack: LiveData<Boolean> = _navigateBack

    fun loadNote(id: Long) = viewModelScope.launch {
        _note.value = repository.getNoteById(id)
    }

    fun deleteNote() = viewModelScope.launch {
        _note.value?.let { repository.deleteNote(it) }
        _navigateBack.value = true
    }

    fun onNavigatedBack() {
        _navigateBack.value = false
    }

    fun toggleFavorite() = viewModelScope.launch {
        _note.value?.let { current ->
            val updated = current.copy(
                isFavorite = !current.isFavorite,
                updatedAt = System.currentTimeMillis()
            )
            repository.updateNote(updated)
            _note.postValue(updated)
        }
    }
}
