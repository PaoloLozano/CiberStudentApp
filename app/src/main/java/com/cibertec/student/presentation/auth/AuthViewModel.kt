package com.cibertec.student.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cibertec.student.domain.model.User
import com.cibertec.student.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val user: User? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        if (!validateLoginInput(email, password)) return
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            authRepository.login(email, password)
                .onSuccess { user ->
                    _uiState.value = AuthUiState(isSuccess = true, user = user)
                }
                .onFailure { error ->
                    _uiState.value = AuthUiState(error = mapFirebaseError(error.message))
                }
        }
    }

    fun register(
        name: String,
        studentCode: String,
        career: String,
        cycle: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (!validateRegisterInput(name, email, password, confirmPassword)) return
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val user = User(name = name, studentCode = studentCode, career = career, cycle = cycle)
            authRepository.register(email, password, user)
                .onSuccess { newUser ->
                    _uiState.value = AuthUiState(isSuccess = true, user = newUser)
                }
                .onFailure { error ->
                    _uiState.value = AuthUiState(error = mapFirebaseError(error.message))
                }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            authRepository.resetPassword(email)
                .onSuccess {
                    _uiState.value = AuthUiState(isSuccess = true)
                }
                .onFailure { error ->
                    _uiState.value = AuthUiState(error = error.message)
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun validateLoginInput(email: String, password: String): Boolean {
        if (email.isBlank()) {
            _uiState.value = AuthUiState(error = "Ingresa tu correo electrónico")
            return false
        }
        if (password.isBlank()) {
            _uiState.value = AuthUiState(error = "Ingresa tu contraseña")
            return false
        }
        return true
    }

    private fun validateRegisterInput(
        name: String, email: String, password: String, confirmPassword: String
    ): Boolean {
        if (name.isBlank()) {
            _uiState.value = AuthUiState(error = "Ingresa tu nombre completo")
            return false
        }
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = AuthUiState(error = "Ingresa un correo válido")
            return false
        }
        if (password.length < 6) {
            _uiState.value = AuthUiState(error = "La contraseña debe tener al menos 6 caracteres")
            return false
        }
        if (password != confirmPassword) {
            _uiState.value = AuthUiState(error = "Las contraseñas no coinciden")
            return false
        }
        return true
    }

    private fun mapFirebaseError(message: String?): String {
        return when {
            message == null -> "Error desconocido"
            message.contains("password") -> "Contraseña incorrecta"
            message.contains("email") -> "Correo no registrado"
            message.contains("network") -> "Sin conexión a internet"
            message.contains("already in use") -> "Este correo ya está registrado"
            else -> "Error al iniciar sesión. Intenta de nuevo"
        }
    }
}
