package com.cibertec.student.domain.repository

import com.cibertec.student.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCourses(userId: String): Flow<List<Course>>
    fun getCoursesForDay(userId: String, dayOfWeek: Int): Flow<List<Course>>
    suspend fun getCourseById(id: Long): Course?
    suspend fun insertCourse(course: Course): Long
    suspend fun updateCourse(course: Course)
    suspend fun deleteCourse(course: Course)
    suspend fun getCourseCount(userId: String): Int
}
