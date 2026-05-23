package com.cibertec.student.domain.repository

import com.cibertec.student.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(userId: String): Flow<List<Note>>
    fun getNotesByCourse(userId: String, courseId: Long): Flow<List<Note>>
    fun searchNotes(userId: String, query: String): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}
