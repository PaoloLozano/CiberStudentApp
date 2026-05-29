package com.cibertec.student.data.repository

import com.cibertec.student.domain.model.User
import com.cibertec.student.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return Result.failure(Exception("Error de autenticación"))
            val userDoc = firestore.collection("users").document(firebaseUser.uid).get().await()
            val user = userDoc.toObject(User::class.java) ?: User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = firebaseUser.displayName ?: "Estudiante"
            )
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String, user: User): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return Result.failure(Exception("Error al crear cuenta"))
            val newUser = user.copy(uid = firebaseUser.uid, email = email)
            firestore.collection("users").document(firebaseUser.uid).set(newUser).await()
            Result.success(newUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun getCurrentUser(): User? {
        val firebaseUser = firebaseAuth.currentUser ?: return null
        return try {
            val doc = firestore.collection("users").document(firebaseUser.uid).get().await()
            doc.toObject(User::class.java) ?: User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = firebaseUser.displayName ?: "Estudiante"
            )
        } catch (e: Exception) {
            User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = firebaseUser.displayName ?: "Estudiante"
            )
        }
    }

    override fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null

    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(user: User): Result<User> {
        return try {
            firestore.collection("users").document(user.uid).set(user).await()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
