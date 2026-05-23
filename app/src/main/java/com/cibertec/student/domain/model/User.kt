package com.cibertec.student.domain.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val studentCode: String = "",
    val career: String = "",
    val cycle: String = "",
    val avatarUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
