package com.cibertec.student.domain.repository

import com.cibertec.student.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(userId: String): Flow<List<Task>>
    fun getTasksByStatus(userId: String, status: Task.TaskStatus): Flow<List<Task>>
    fun getPendingTaskCount(userId: String): Flow<Int>
    suspend fun getTaskById(id: Long): Task?
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun markTaskComplete(id: Long)
}
