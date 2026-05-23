package com.cibertec.student.data.local.dao

import androidx.room.*
import com.cibertec.student.data.local.entity.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    @Query("SELECT * FROM attendance WHERE userId = :userId ORDER BY date DESC")
    fun getAttendanceRecords(userId: String): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE userId = :userId AND courseId = :courseId ORDER BY date DESC")
    fun getAttendanceByCourse(userId: String, courseId: Long): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE id = :id")
    suspend fun getAttendanceById(id: Long): AttendanceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(record: AttendanceEntity): Long

    @Update
    suspend fun updateAttendance(record: AttendanceEntity)

    @Delete
    suspend fun deleteAttendance(record: AttendanceEntity)

    @Query("SELECT COUNT(*) FROM attendance WHERE userId = :userId AND status = 'PRESENT'")
    suspend fun getTotalPresent(userId: String): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE userId = :userId AND status = 'ABSENT'")
    suspend fun getTotalAbsent(userId: String): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE userId = :userId AND courseId = :courseId AND status = 'PRESENT'")
    suspend fun getPresentCountByCourse(userId: String, courseId: Long): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE userId = :userId AND courseId = :courseId AND status = 'ABSENT'")
    suspend fun getAbsentCountByCourse(userId: String, courseId: Long): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE userId = :userId AND courseId = :courseId AND status = 'JUSTIFIED'")
    suspend fun getJustifiedCountByCourse(userId: String, courseId: Long): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE userId = :userId AND courseId = :courseId")
    suspend fun getTotalByCourse(userId: String, courseId: Long): Int
}
