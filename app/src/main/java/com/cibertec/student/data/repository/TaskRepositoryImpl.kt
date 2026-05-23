package com.cibertec.student.data.repository

import com.cibertec.student.data.local.dao.TaskDao
import com.cibertec.student.data.local.entity.toDomain
import com.cibertec.student.data.local.entity.toEntity
import com.cibertec.student.domain.model.Task
import com.cibertec.student.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getTasks(userId: String): Flow<List<Task>> =
        taskDao.getTasks(userId).map { list -> list.map { it.toDomain() } }

    override fun getTasksByStatus(userId: String, status: Task.TaskStatus): Flow<List<Task>> =
        taskDao.getTasksByStatus(userId, status.name).map { list -> list.map { it.toDomain() } }

    override fun getPendingTaskCount(userId: String): Flow<Int> =
        taskDao.getPendingTaskCount(userId)

    override suspend fun getTaskById(id: Long): Task? =
        taskDao.getTaskById(id)?.toDomain()

    override suspend fun insertTask(task: Task): Long =
        taskDao.insertTask(task.toEntity())

    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(task.toEntity())

    override suspend fun deleteTask(task: Task) =
        taskDao.deleteTask(task.toEntity())

    override suspend fun markTaskComplete(id: Long) =
        taskDao.markTaskComplete(id)
}
