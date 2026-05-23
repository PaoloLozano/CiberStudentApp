package com.cibertec.student.domain.model

data class Note(
    val id: Long = 0,
    val userId: String = "",
    val title: String = "",
    val content: String = "",
    val courseId: Long = 0,
    val courseName: String = "",
    val color: String = "#FFFFFF",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
