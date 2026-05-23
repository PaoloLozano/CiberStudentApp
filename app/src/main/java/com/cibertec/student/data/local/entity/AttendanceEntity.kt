package com.cibertec.student.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cibertec.student.domain.model.AttendanceRecord

@Entity(tableName = "attendance")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val courseId: Long,
    val courseName: String,
    val date: Long = System.currentTimeMillis(),
    val status: String = AttendanceRecord.AttendanceStatus.PRESENT.name,
    val notes: String = ""
)

fun AttendanceEntity.toDomain() = AttendanceRecord(
    id = id,
    userId = userId,
    courseId = courseId,
    courseName = courseName,
    date = date,
    status = AttendanceRecord.AttendanceStatus.valueOf(status),
    notes = notes
)

fun AttendanceRecord.toEntity() = AttendanceEntity(
    id = id,
    userId = userId,
    courseId = courseId,
    courseName = courseName,
    date = date,
    status = status.name,
    notes = notes
)
