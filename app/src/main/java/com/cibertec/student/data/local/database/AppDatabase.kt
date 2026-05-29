package com.cibertec.student.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cibertec.student.data.local.converter.Converters
import com.cibertec.student.data.local.dao.*
import com.cibertec.student.data.local.entity.*

@Database(
    entities = [
        CourseEntity::class,
        TaskEntity::class,
        NoteEntity::class,
        AttendanceEntity::class

    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun taskDao(): TaskDao
    abstract fun noteDao(): NoteDao
    abstract fun attendanceDao(): AttendanceDao

    companion object {
        const val DATABASE_NAME = "ciber_student_db"
    }
}
