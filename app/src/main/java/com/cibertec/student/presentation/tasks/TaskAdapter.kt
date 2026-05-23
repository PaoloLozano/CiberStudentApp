package com.cibertec.student.presentation.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.student.R
import com.cibertec.student.databinding.ItemTaskBinding
import com.cibertec.student.domain.model.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onCheckClick: (Task) -> Unit,
    private val onDeleteClick: ((Task) -> Unit)? = null
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                tvTaskTitle.text = task.title
                tvTaskDescription.text = task.description
                tvCourseTag.text = task.courseName.ifBlank { "General" }

                // Priority chip
                when (task.priority) {
                    Task.Priority.HIGH -> {
                        tvPriority.text = "Alta"
                        tvPriority.setTextColor(ContextCompat.getColor(root.context, R.color.error))
                        tvPriority.setBackgroundResource(R.drawable.bg_chip_priority_high)
                    }
                    Task.Priority.MEDIUM -> {
                        tvPriority.text = "Media"
                        tvPriority.setTextColor(ContextCompat.getColor(root.context, R.color.warning))
                        tvPriority.setBackgroundResource(R.drawable.bg_chip_priority_medium)
                    }
                    Task.Priority.LOW -> {
                        tvPriority.text = "Baja"
                        tvPriority.setTextColor(ContextCompat.getColor(root.context, R.color.success))
                        tvPriority.setBackgroundResource(R.drawable.bg_chip_priority_low)
                    }
                }

                // Due date
                if (task.dueDate > 0) {
                    val sdf = SimpleDateFormat("EEE, dd MMM", Locale("es", "PE"))
                    tvDueDate.text = sdf.format(Date(task.dueDate))
                    val isOverdue = task.dueDate < System.currentTimeMillis() &&
                            task.status != Task.TaskStatus.COMPLETED
                    tvDueDate.setTextColor(
                        ContextCompat.getColor(root.context,
                            if (isOverdue) R.color.error else R.color.text_hint)
                    )
                } else {
                    tvDueDate.text = "Sin fecha"
                }

                // Completed state
                val isCompleted = task.status == Task.TaskStatus.COMPLETED
                cbTaskDone.isChecked = isCompleted
                if (isCompleted) {
                    tvTaskTitle.paintFlags = tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvTaskTitle.alpha = 0.5f
                } else {
                    tvTaskTitle.paintFlags = tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    tvTaskTitle.alpha = 1f
                }

                cbTaskDone.setOnClickListener { onCheckClick(task) }
                root.setOnClickListener { onTaskClick(task) }
                root.setOnLongClickListener {
                    onDeleteClick?.invoke(task)
                    true
                }
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}
