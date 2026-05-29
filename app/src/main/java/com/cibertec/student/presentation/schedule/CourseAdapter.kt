package com.cibertec.student.presentation.schedule

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.student.databinding.ItemCourseBinding
import com.cibertec.student.domain.model.Course

class CourseAdapter(
    private val onCourseClick: (Course) -> Unit,
    private val onCourseDelete: (Course) -> Unit
) : ListAdapter<Course, CourseAdapter.CourseViewHolder>(CourseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.apply {
                tvCourseName.text = course.name
                tvTeacher.text = course.teacher
                tvClassroom.text = course.classroom
                tvCredits.text = course.credits.toString()

                val days = course.days.mapNotNull { day ->
                    listOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb").getOrNull(day - 1)
                }.joinToString(", ")
                tvScheduleTime.text = "$days  ${course.startTime} - ${course.endTime}"

                try {
                    viewColorIndicator.setBackgroundColor(Color.parseColor(course.color))
                } catch (e: Exception) {
                    viewColorIndicator.setBackgroundColor(Color.parseColor("#1565C0"))
                }

                root.setOnClickListener { onCourseClick(course) }
                btnDeleteCourse.setOnClickListener { onCourseDelete(course) }
            }
        }
    }

    class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Course, newItem: Course) = oldItem == newItem
    }
}
