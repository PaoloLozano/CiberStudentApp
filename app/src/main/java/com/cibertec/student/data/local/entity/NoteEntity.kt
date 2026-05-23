package com.cibertec.student.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cibertec.student.domain.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val title: String,
    val content: String,
    val courseId: Long = 0,
    val courseName: String = "",
    val color: String = "#FFFFFF",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

fun NoteEntity.toDomain() = Note(
    id = id,
    userId = userId,
    title = title,
    content = content,
    courseId = courseId,
    courseName = courseName,
    color = color,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Note.toEntity() = NoteEntity(
    id = id,
    userId = userId,
    title = title,
    content = content,
    courseId = courseId,
    courseName = courseName,
    color = color,
    createdAt = createdAt,
    updatedAt = updatedAt
)
