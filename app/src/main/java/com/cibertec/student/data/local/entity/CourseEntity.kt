package com.cibertec.student.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cibertec.student.data.local.converter.Converters
import com.cibertec.student.domain.model.Course

@Entity(tableName = "courses")
@TypeConverters(Converters::class)
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val name: String,
    val teacher: String,
    val classroom: String,
    val startTime: String,
    val endTime: String,
    val days: List<Int>,
    val credits: Int,
    val color: String = "#1565C0",
    val createdAt: Long = System.currentTimeMillis()
)

fun CourseEntity.toDomain() = Course(
    id = id,
    userId = userId,
    name = name,
    teacher = teacher,
    classroom = classroom,
    startTime = startTime,
    endTime = endTime,
    days = days,
    credits = credits,
    color = color,
    createdAt = createdAt
)

fun Course.toEntity() = CourseEntity(
    id = id,
    userId = userId,
    name = name,
    teacher = teacher,
    classroom = classroom,
    startTime = startTime,
    endTime = endTime,
    days = days,
    credits = credits,
    color = color,
    createdAt = createdAt
)
