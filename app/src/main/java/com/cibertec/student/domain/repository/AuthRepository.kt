package com.cibertec.student.domain.repository

import com.cibertec.student.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String, user: User): Result<User>
    suspend fun logout()
    suspend fun getCurrentUser(): User?
    fun isLoggedIn(): Boolean
    suspend fun resetPassword(email: String): Result<Unit>
    suspend fun updateProfile(user: User): Result<User>
}
