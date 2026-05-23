package com.cibertec.student.domain.model

data class Course(
    val id: Long = 0,
    val userId: String = "",
    val name: String = "",
    val teacher: String = "",
    val classroom: String = "",
    val startTime: String = "",   // "07:00"
    val endTime: String = "",     // "09:00"
    val days: List<Int> = emptyList(), // 1=Mon, 2=Tue, ..., 7=Sun
    val credits: Int = 0,
    val color: String = "#1565C0",
    val createdAt: Long = System.currentTimeMillis()
)
