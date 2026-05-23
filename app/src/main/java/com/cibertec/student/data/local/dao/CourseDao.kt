package com.cibertec.student.data.local.dao

import androidx.room.*
import com.cibertec.student.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM courses WHERE userId = :userId ORDER BY startTime ASC")
    fun getCourses(userId: String): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE userId = :userId AND days LIKE '%' || :day || '%' ORDER BY startTime ASC")
    fun getCoursesForDay(userId: String, day: String): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getCourseById(id: Long): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity): Long

    @Update
    suspend fun updateCourse(course: CourseEntity)

    @Delete
    suspend fun deleteCourse(course: CourseEntity)

    @Query("SELECT COUNT(*) FROM courses WHERE userId = :userId")
    suspend fun getCourseCount(userId: String): Int

    @Query("SELECT * FROM courses WHERE userId = :userId")
    suspend fun getAllCoursesList(userId: String): List<CourseEntity>
}
