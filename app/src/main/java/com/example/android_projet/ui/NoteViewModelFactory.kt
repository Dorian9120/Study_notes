package com.example.android_projet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_projet.data.repository.NoteRepository
import com.example.android_projet.ui.notedetail.NoteDetailViewModel
import com.example.android_projet.ui.noteedit.NoteEditViewModel
import com.example.android_projet.ui.notelist.NoteListViewModel

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(NoteListViewModel::class.java) -> NoteListViewModel(repository) as T
        modelClass.isAssignableFrom(NoteDetailViewModel::class.java) -> NoteDetailViewModel(repository) as T
        modelClass.isAssignableFrom(NoteEditViewModel::class.java) -> NoteEditViewModel(repository) as T
        else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
    }
}
