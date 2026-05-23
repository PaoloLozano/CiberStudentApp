package com.cibertec.student.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cibertec.student.core.notifications.NotificationScheduler
import com.cibertec.student.domain.model.Task
import com.cibertec.student.domain.repository.AuthRepository
import com.cibertec.student.domain.repository.CourseRepository
import com.cibertec.student.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TasksUiState(
    val isLoading: Boolean = true,
    val tasks: List<Task> = emptyList(),
    val filteredTasks: List<Task> = emptyList(),
    val filterType: FilterType = FilterType.ALL,
    val error: String? = null,
    val successMessage: String? = null
)

enum class FilterType { ALL, PENDING, IN_PROGRESS, COMPLETED }

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository,
    private val courseRepository: CourseRepository,
    private val notificationScheduler: NotificationScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    private var userId: String = ""

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser() ?: return@launch
            userId = user.uid
            taskRepository.getTasks(userId)
                .collect { tasks ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            tasks = tasks,
                            filteredTasks = filterTasks(tasks, it.filterType)
                        )
                    }
                }
        }
    }

    fun applyFilter(filterType: FilterType) {
        _uiState.update {
            it.copy(
                filterType = filterType,
                filteredTasks = filterTasks(it.tasks, filterType)
            )
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                val taskWithUser = task.copy(userId = userId)
                val id = taskRepository.insertTask(taskWithUser)
                if (task.reminderEnabled && task.dueDate > 0) {
                    notificationScheduler.scheduleTaskReminder(task.copy(id = id))
                }
                _uiState.update { it.copy(successMessage = "Tarea agregada") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al agregar tarea") }
            }
        }
    }

    fun markComplete(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.markTaskComplete(task.id)
                notificationScheduler.cancelTaskReminder(task.id)
                _uiState.update { it.copy(successMessage = "¡Tarea completada!") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al actualizar tarea") }
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task)
                notificationScheduler.cancelTaskReminder(task.id)
                _uiState.update { it.copy(successMessage = "Tarea eliminada") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al eliminar tarea") }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(error = null, successMessage = null) }
    }

    private fun filterTasks(tasks: List<Task>, filterType: FilterType): List<Task> {
        return when (filterType) {
            FilterType.ALL -> tasks
            FilterType.PENDING -> tasks.filter { it.status == Task.TaskStatus.PENDING }
            FilterType.IN_PROGRESS -> tasks.filter { it.status == Task.TaskStatus.IN_PROGRESS }
            FilterType.COMPLETED -> tasks.filter { it.status == Task.TaskStatus.COMPLETED }
        }
    }
}
