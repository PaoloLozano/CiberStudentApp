package com.cibertec.student.data.repository

import com.cibertec.student.data.local.dao.AttendanceDao
import com.cibertec.student.data.local.dao.CourseDao
import com.cibertec.student.data.local.entity.toDomain
import com.cibertec.student.data.local.entity.toEntity
import com.cibertec.student.domain.model.AttendanceRecord
import com.cibertec.student.domain.model.CourseAttendanceSummary
import com.cibertec.student.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceDao: AttendanceDao,
    private val courseDao: CourseDao
) : AttendanceRepository {

    override fun getAttendanceRecords(userId: String): Flow<List<AttendanceRecord>> =
        attendanceDao.getAttendanceRecords(userId).map { list -> list.map { it.toDomain() } }

    override fun getAttendanceByCourse(userId: String, courseId: Long): Flow<List<AttendanceRecord>> =
        attendanceDao.getAttendanceByCourse(userId, courseId).map { list -> list.map { it.toDomain() } }

    override fun getAttendanceSummaries(userId: String): Flow<List<CourseAttendanceSummary>> =
        courseDao.getCourses(userId).map { courses ->
            courses.map { courseEntity ->
                val present = attendanceDao.getPresentCountByCourse(userId, courseEntity.id)
                val absent = attendanceDao.getAbsentCountByCourse(userId, courseEntity.id)
                val justified = attendanceDao.getJustifiedCountByCourse(userId, courseEntity.id)
                val total = attendanceDao.getTotalByCourse(userId, courseEntity.id)
                CourseAttendanceSummary(
                    courseId = courseEntity.id,
                    courseName = courseEntity.name,
                    courseColor = courseEntity.color,
                    totalClasses = total,
                    presentCount = present,
                    absentCount = absent,
                    justifiedCount = justified
                )
            }
        }

    override suspend fun insertAttendance(record: AttendanceRecord): Long =
        attendanceDao.insertAttendance(record.toEntity())

    override suspend fun updateAttendance(record: AttendanceRecord) =
        attendanceDao.updateAttendance(record.toEntity())

    override suspend fun deleteAttendance(record: AttendanceRecord) =
        attendanceDao.deleteAttendance(record.toEntity())

    override suspend fun getTotalPresent(userId: String): Int =
        attendanceDao.getTotalPresent(userId)

    override suspend fun getTotalAbsent(userId: String): Int =
        attendanceDao.getTotalAbsent(userId)
}
