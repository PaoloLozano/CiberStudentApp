package com.cibertec.student.domain.repository

import com.cibertec.student.domain.model.AttendanceRecord
import com.cibertec.student.domain.model.CourseAttendanceSummary
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    fun getAttendanceRecords(userId: String): Flow<List<AttendanceRecord>>
    fun getAttendanceByCourse(userId: String, courseId: Long): Flow<List<AttendanceRecord>>
    fun getAttendanceSummaries(userId: String): Flow<List<CourseAttendanceSummary>>
    suspend fun insertAttendance(record: AttendanceRecord): Long
    suspend fun updateAttendance(record: AttendanceRecord)
    suspend fun deleteAttendance(record: AttendanceRecord)
    suspend fun getTotalPresent(userId: String): Int
    suspend fun getTotalAbsent(userId: String): Int
}
