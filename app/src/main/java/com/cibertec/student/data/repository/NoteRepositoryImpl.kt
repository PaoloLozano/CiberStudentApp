package com.cibertec.student.data.repository

import com.cibertec.student.data.local.dao.NoteDao
import com.cibertec.student.data.local.entity.toDomain
import com.cibertec.student.data.local.entity.toEntity
import com.cibertec.student.domain.model.Note
import com.cibertec.student.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getNotes(userId: String): Flow<List<Note>> =
        noteDao.getNotes(userId).map { list -> list.map { it.toDomain() } }

    override fun getNotesByCourse(userId: String, courseId: Long): Flow<List<Note>> =
        noteDao.getNotesByCourse(userId, courseId).map { list -> list.map { it.toDomain() } }

    override fun searchNotes(userId: String, query: String): Flow<List<Note>> =
        noteDao.searchNotes(userId, query).map { list -> list.map { it.toDomain() } }

    override suspend fun getNoteById(id: Long): Note? =
        noteDao.getNoteById(id)?.toDomain()

    override suspend fun insertNote(note: Note): Long =
        noteDao.insertNote(note.toEntity())

    override suspend fun updateNote(note: Note) =
        noteDao.updateNote(note.toEntity())

    override suspend fun deleteNote(note: Note) =
        noteDao.deleteNote(note.toEntity())
}
