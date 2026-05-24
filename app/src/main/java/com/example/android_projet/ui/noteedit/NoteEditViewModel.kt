package com.example.android_projet.ui.noteedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_projet.data.model.Note
import com.example.android_projet.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteEditViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _note = MutableLiveData<Note?>()
    val note: LiveData<Note?> = _note

    private val _navigateBack = MutableLiveData(false)
    val navigateBack: LiveData<Boolean> = _navigateBack

    val modules: LiveData<List<String>> = repository.getModules().asLiveData()

    fun loadNote(id: Long) = viewModelScope.launch {
        if (id > 0) _note.value = repository.getNoteById(id)
    }

    fun saveNote(title: String, content: String, module: String) = viewModelScope.launch {
        val existing = _note.value
        if (existing != null) {
            repository.updateNote(
                existing.copy(
                    title = title,
                    content = content,
                    module = module,
                    updatedAt = System.currentTimeMillis()
                )
            )
        } else {
            repository.insertNote(Note(title = title, content = content, module = module))
        }
        _navigateBack.value = true
    }

    fun onNavigatedBack() {
        _navigateBack.value = false
    }
}
