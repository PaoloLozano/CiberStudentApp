package com.cibertec.student.presentation.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cibertec.student.domain.model.AttendanceRecord
import com.cibertec.student.domain.model.CourseAttendanceSummary
import com.cibertec.student.domain.repository.AttendanceRepository
import com.cibertec.student.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AttendanceUiState(
    val isLoading: Boolean = true,
    val summaries: List<CourseAttendanceSummary> = emptyList(),
    val totalPresent: Int = 0,
    val totalAbsent: Int = 0,
    val hasAtRiskCourses: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AttendanceUiState())
    val uiState: StateFlow<AttendanceUiState> = _uiState.asStateFlow()

    private var userId: String = ""

    init {
        loadAttendance()
    }

    private fun loadAttendance() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser() ?: return@launch
            userId = user.uid
            attendanceRepository.getAttendanceSummaries(userId)
                .collect { summaries ->
                    val totalPresent = attendanceRepository.getTotalPresent(userId)
                    val totalAbsent = attendanceRepository.getTotalAbsent(userId)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            summaries = summaries,
                            totalPresent = totalPresent,
                            totalAbsent = totalAbsent,
                            hasAtRiskCourses = summaries.any { s -> s.isAtRisk }
                        )
                    }
                }
        }
    }

    fun markAttendance(courseId: Long, courseName: String, status: AttendanceRecord.AttendanceStatus) {
        viewModelScope.launch {
            try {
                val record = AttendanceRecord(
                    userId = userId,
                    courseId = courseId,
                    courseName = courseName,
                    date = System.currentTimeMillis(),
                    status = status
                )
                attendanceRepository.insertAttendance(record)
                val statusText = when (status) {
                    AttendanceRecord.AttendanceStatus.PRESENT -> "Asistencia registrada ✓"
                    AttendanceRecord.AttendanceStatus.ABSENT -> "Falta registrada"
                    AttendanceRecord.AttendanceStatus.JUSTIFIED -> "Falta justificada registrada"
                }
                _uiState.update { it.copy(successMessage = statusText) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al registrar asistencia") }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(error = null, successMessage = null) }
    }
}
