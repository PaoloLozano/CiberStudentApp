package com.cibertec.student.domain.model

data class Task(
    val id: Long = 0,
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val courseId: Long = 0,
    val courseName: String = "",
    val dueDate: Long = 0L,
    val priority: Priority = Priority.MEDIUM,
    val status: TaskStatus = TaskStatus.PENDING,
    val reminderEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    enum class Priority { HIGH, MEDIUM, LOW }
    enum class TaskStatus { PENDING, IN_PROGRESS, COMPLETED }
}
