package com.cibertec.student.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cibertec.student.domain.model.Note
import com.cibertec.student.domain.repository.AuthRepository
import com.cibertec.student.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotesUiState(
    val isLoading: Boolean = true,
    val notes: List<Note> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private var userId: String = ""

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser() ?: return@launch
            userId = user.uid
            noteRepository.getNotes(userId)
                .collect { notes ->
                    _uiState.update { it.copy(isLoading = false, notes = notes) }
                }
        }
    }

    fun search(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isBlank()) {
            loadNotes()
        } else {
            viewModelScope.launch {
                noteRepository.searchNotes(userId, query)
                    .collect { notes ->
                        _uiState.update { it.copy(notes = notes) }
                    }
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            try {
                noteRepository.insertNote(note.copy(userId = userId))
                _uiState.update { it.copy(successMessage = "Nota guardada") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al guardar nota") }
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(note)
                _uiState.update { it.copy(successMessage = "Nota eliminada") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al eliminar nota") }
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                noteRepository.updateNote(note.copy(updatedAt = System.currentTimeMillis()))
                _uiState.update { it.copy(successMessage = "Nota actualizada") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al actualizar nota") }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(error = null, successMessage = null) }
    }
}
