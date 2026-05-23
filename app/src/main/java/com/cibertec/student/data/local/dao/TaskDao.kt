package com.cibertec.student.data.local.dao

import androidx.room.*
import com.cibertec.student.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE userId = :userId ORDER BY dueDate ASC, priority ASC")
    fun getTasks(userId: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE userId = :userId AND status = :status ORDER BY dueDate ASC")
    fun getTasksByStatus(userId: String, status: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE userId = :userId AND status != 'COMPLETED' ORDER BY dueDate ASC LIMIT 5")
    fun getRecentPendingTasks(userId: String): Flow<List<TaskEntity>>

    @Query("SELECT COUNT(*) FROM tasks WHERE userId = :userId AND status = 'PENDING'")
    fun getPendingTaskCount(userId: String): Flow<Int>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("UPDATE tasks SET status = 'COMPLETED' WHERE id = :id")
    suspend fun markTaskComplete(id: Long)
}
