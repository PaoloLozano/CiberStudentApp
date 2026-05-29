package com.cibertec.student.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cibertec.student.domain.model.AttendanceRecord
import com.cibertec.student.domain.model.Task
import com.cibertec.student.domain.model.User
import com.cibertec.student.domain.repository.AttendanceRepository
import com.cibertec.student.domain.repository.AuthRepository
import com.cibertec.student.domain.repository.CourseRepository
import com.cibertec.student.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val user: User? = null,
    val totalCourses: Int = 0,
    val completedTasks: Int = 0,
    val attendancePercentage: Int = 0,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val successMessage: String? = null,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val courseRepository: CourseRepository,
    private val taskRepository: TaskRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val user = authRepository.getCurrentUser()
                if (user != null) {
                    // Cargar estadísticas
                    val courses = courseRepository.getCourses(user.uid).first()
                    val tasks = taskRepository.getTasks(user.uid).first()
                    val completedTasks = tasks.count { it.status == Task.TaskStatus.COMPLETED }

                    // Calcular % de asistencia global
                    val attendance = attendanceRepository.getAttendanceRecords(user.uid).first()
                    val attendancePct = if (attendance.isNotEmpty()) {
                        val present = attendance.count { it.status == AttendanceRecord.AttendanceStatus.PRESENT }
                        (present * 100) / attendance.size
                    } else 0

                    _uiState.value = _uiState.value.copy(
                        user = user,
                        totalCourses = courses.size,
                        completedTasks = completedTasks,
                        attendancePercentage = attendancePct,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun updateProfile(name: String, career: String, studentCode: String, cycle: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)
            try {
                val currentUser = _uiState.value.user ?: return@launch
                val updatedUser = currentUser.copy(
                    name = name,
                    career = career,
                    studentCode = studentCode,
                    cycle = cycle
                )
                // Guardar en Firestore
                authRepository.updateProfile(updatedUser)
                _uiState.value = _uiState.value.copy(
                    user = updatedUser,
                    isSaving = false,
                    successMessage = "Perfil actualizado correctamente"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = "Error al guardar: ${e.message}"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(successMessage = null, error = null)
    }
}
