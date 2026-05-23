package com.cibertec.student.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cibertec.student.domain.model.Course
import com.cibertec.student.domain.repository.AuthRepository
import com.cibertec.student.domain.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScheduleUiState(
    val isLoading: Boolean = true,
    val courses: List<Course> = emptyList(),
    val selectedDayCourses: List<Course> = emptyList(),
    val selectedDay: Int = 2, // Monday by default
    val error: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    private var userId: String = ""

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser() ?: return@launch
            userId = user.uid
            courseRepository.getCourses(userId)
                .collect { courses ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            courses = courses,
                            selectedDayCourses = filterByDay(courses, it.selectedDay)
                        )
                    }
                }
        }
    }

    fun selectDay(day: Int) {
        _uiState.update {
            it.copy(
                selectedDay = day,
                selectedDayCourses = filterByDay(it.courses, day)
            )
        }
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            try {
                val courseWithUser = course.copy(userId = userId)
                courseRepository.insertCourse(courseWithUser)
                _uiState.update { it.copy(successMessage = "Curso agregado exitosamente") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al agregar curso") }
            }
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            try {
                courseRepository.deleteCourse(course)
                _uiState.update { it.copy(successMessage = "Curso eliminado") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al eliminar curso") }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(error = null, successMessage = null) }
    }

    private fun filterByDay(courses: List<Course>, day: Int): List<Course> =
        courses.filter { it.days.contains(day) }.sortedBy { it.startTime }
}
