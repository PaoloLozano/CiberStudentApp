package com.cibertec.student.presentation.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.student.databinding.ItemTodayCourseBinding
import com.cibertec.student.domain.model.Course
import java.util.Calendar

class TodayCourseAdapter : ListAdapter<Course, TodayCourseAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodayCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemTodayCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.tvCourseName.text = course.name
            binding.tvClassroom.text = course.classroom
            binding.tvStartTime.text = course.startTime
            binding.tvEndTime.text = course.endTime

            // Check if class is happening now
            val isNow = isCurrentlyInClass(course)
            binding.tvNow.visibility = if (isNow) View.VISIBLE else View.GONE

            try {
                binding.viewDivider.setBackgroundColor(Color.parseColor(course.color))
            } catch (e: Exception) {
                binding.viewDivider.setBackgroundColor(Color.parseColor("#1565C0"))
            }
        }

        private fun isCurrentlyInClass(course: Course): Boolean {
            val now = Calendar.getInstance()
            val currentHour = now.get(Calendar.HOUR_OF_DAY)
            val currentMinute = now.get(Calendar.MINUTE)
            val currentTime = currentHour * 60 + currentMinute

            return try {
                val (startH, startM) = course.startTime.split(":").map { it.toInt() }
                val (endH, endM) = course.endTime.split(":").map { it.toInt() }
                val startTime = startH * 60 + startM
                val endTime = endH * 60 + endM
                currentTime in startTime..endTime
            } catch (e: Exception) { false }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(old: Course, new: Course) = old.id == new.id
        override fun areContentsTheSame(old: Course, new: Course) = old == new
    }
}
