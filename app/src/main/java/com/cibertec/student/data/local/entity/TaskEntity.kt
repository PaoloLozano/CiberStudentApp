package com.cibertec.student.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cibertec.student.domain.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val title: String,
    val description: String,
    val courseId: Long = 0,
    val courseName: String = "",
    val dueDate: Long = 0L,
    val priority: String = Task.Priority.MEDIUM.name,
    val status: String = Task.TaskStatus.PENDING.name,
    val reminderEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

fun TaskEntity.toDomain() = Task(
    id = id,
    userId = userId,
    title = title,
    description = description,
    courseId = courseId,
    courseName = courseName,
    dueDate = dueDate,
    priority = Task.Priority.valueOf(priority),
    status = Task.TaskStatus.valueOf(status),
    reminderEnabled = reminderEnabled,
    createdAt = createdAt
)

fun Task.toEntity() = TaskEntity(
    id = id,
    userId = userId,
    title = title,
    description = description,
    courseId = courseId,
    courseName = courseName,
    dueDate = dueDate,
    priority = priority.name,
    status = status.name,
    reminderEnabled = reminderEnabled,
    createdAt = createdAt
)
