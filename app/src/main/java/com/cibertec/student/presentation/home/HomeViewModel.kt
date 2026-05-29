package com.cibertec.student.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cibertec.student.domain.model.Course
import com.cibertec.student.domain.model.Task
import com.cibertec.student.domain.model.User
import com.cibertec.student.domain.repository.AuthRepository
import com.cibertec.student.domain.repository.CourseRepository
import com.cibertec.student.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val todayCourses: List<Course> = emptyList(),
    val pendingTasks: List<Task> = emptyList(),
    val totalCourses: Int = 0,
    val pendingTaskCount: Int = 0,
    val attendancePercentage: Int = 100
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val courseRepository: CourseRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            _uiState.update { it.copy(user = user, isLoading = false) }

            if (user == null) return@launch
            val uid = user.uid
            val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

            // Cursos del día de hoy — reactivo
            launch {
                courseRepository.getCoursesForDay(uid, dayOfWeek)
                    .collect { courses ->
                        _uiState.update { it.copy(todayCourses = courses) }
                    }
            }

            // Total de cursos — reactivo, se actualiza al agregar/borrar cursos
            launch {
                courseRepository.getCourses(uid)
                    .collect { courses ->
                        _uiState.update { it.copy(totalCourses = courses.size) }
                    }
            }

            // Tareas pendientes — reactivo
            launch {
                taskRepository.getTasks(uid)
                    .collect { tasks ->
                        val allPending = tasks.filter { it.status != Task.TaskStatus.COMPLETED }
                        val preview = allPending.take(5)
                        _uiState.update { it.copy(
                            pendingTasks = preview,
                            pendingTaskCount = allPending.size
                        )}
                    }
            }
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isLoading = true) }
        loadData()
    }
}
