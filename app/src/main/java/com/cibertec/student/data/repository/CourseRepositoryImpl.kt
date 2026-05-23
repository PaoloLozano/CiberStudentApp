package com.cibertec.student.data.repository

import com.cibertec.student.data.local.dao.CourseDao
import com.cibertec.student.data.local.entity.toDomain
import com.cibertec.student.data.local.entity.toEntity
import com.cibertec.student.domain.model.Course
import com.cibertec.student.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao
) : CourseRepository {

    override fun getCourses(userId: String): Flow<List<Course>> =
        courseDao.getCourses(userId).map { list -> list.map { it.toDomain() } }

    override fun getCoursesForDay(userId: String, dayOfWeek: Int): Flow<List<Course>> =
        courseDao.getCoursesForDay(userId, dayOfWeek.toString()).map { list -> list.map { it.toDomain() } }

    override suspend fun getCourseById(id: Long): Course? =
        courseDao.getCourseById(id)?.toDomain()

    override suspend fun insertCourse(course: Course): Long =
        courseDao.insertCourse(course.toEntity())

    override suspend fun updateCourse(course: Course) =
        courseDao.updateCourse(course.toEntity())

    override suspend fun deleteCourse(course: Course) =
        courseDao.deleteCourse(course.toEntity())

    override suspend fun getCourseCount(userId: String): Int =
        courseDao.getCourseCount(userId)
}
