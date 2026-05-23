package com.cibertec.student.domain.model

data class AttendanceRecord(
    val id: Long = 0,
    val userId: String = "",
    val courseId: Long = 0,
    val courseName: String = "",
    val date: Long = System.currentTimeMillis(),
    val status: AttendanceStatus = AttendanceStatus.PRESENT,
    val notes: String = ""
) {
    enum class AttendanceStatus { PRESENT, ABSENT, JUSTIFIED }
}

data class CourseAttendanceSummary(
    val courseId: Long,
    val courseName: String,
    val courseColor: String,
    val totalClasses: Int,
    val presentCount: Int,
    val absentCount: Int,
    val justifiedCount: Int
) {
    val attendancePercentage: Float
        get() = if (totalClasses == 0) 100f else (presentCount + justifiedCount).toFloat() / totalClasses * 100f

    val isAtRisk: Boolean
        get() = attendancePercentage < 75f
}
