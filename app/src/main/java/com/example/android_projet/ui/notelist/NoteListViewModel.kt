package com.example.android_projet.ui.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_projet.data.model.ModuleCount
import com.example.android_projet.data.model.Note
import com.example.android_projet.data.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class NoteListViewModel(private val repository: NoteRepository) : ViewModel() {

    private data class FilterState(
        val query: String = "",
        val module: String? = null,
        val favoritesOnly: Boolean = false
    )

    private val filterState = MutableStateFlow(FilterState())

    val modules: LiveData<List<String>> = repository.getModules().asLiveData()
    val moduleNoteCounts: LiveData<List<ModuleCount>> = repository.getModuleNoteCounts().asLiveData()

    val notes: LiveData<List<Note>> = filterState
        .flatMapLatest { state ->
            when {
                state.query.isNotEmpty() -> repository.searchNotes(state.query)
                state.favoritesOnly -> repository.getFavoriteNotes()
                state.module != null -> repository.getNotesByModule(state.module)
                else -> repository.getAllNotes()
            }
        }
        .asLiveData()

    fun setSearchQuery(query: String) {
        filterState.value = filterState.value.copy(query = query)
    }

    fun setSelectedModule(module: String?) {
        filterState.value = filterState.value.copy(module = module, favoritesOnly = false, query = "")
    }

    fun setShowFavoritesOnly(show: Boolean) {
        filterState.value = filterState.value.copy(favoritesOnly = show, module = null, query = "")
    }

    fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }

    fun toggleFavorite(note: Note) = viewModelScope.launch {
        repository.updateNote(note.copy(isFavorite = !note.isFavorite, updatedAt = System.currentTimeMillis()))
    }
}
