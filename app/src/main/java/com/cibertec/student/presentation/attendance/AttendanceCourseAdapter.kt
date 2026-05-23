package com.cibertec.student.presentation.attendance

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.student.R
import com.cibertec.student.databinding.ItemAttendanceCourseBinding
import com.cibertec.student.domain.model.CourseAttendanceSummary

class AttendanceCourseAdapter(
    private val onPresent: (CourseAttendanceSummary) -> Unit,
    private val onAbsent: (CourseAttendanceSummary) -> Unit
) : ListAdapter<CourseAttendanceSummary, AttendanceCourseAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttendanceCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemAttendanceCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(summary: CourseAttendanceSummary) {
            binding.apply {
                tvCourseName.text = summary.courseName
                tvPresentCount.text = "${summary.presentCount} presentes"
                tvAbsentCount.text = "${summary.absentCount} faltas"
                tvPercentage.text = "${summary.attendancePercentage.toInt()}%"

                // Color percentage based on status
                val percentColor = when {
                    summary.attendancePercentage >= 80f -> R.color.success
                    summary.attendancePercentage >= 70f -> R.color.warning
                    else -> R.color.error
                }
                tvPercentage.setTextColor(binding.root.context.getColor(percentColor))

                // Progress bar
                progressAttendance.progress = summary.attendancePercentage.toInt()
                val indicatorColor = binding.root.context.getColor(percentColor)
                progressAttendance.setIndicatorColor(indicatorColor)

                // Color indicator
                try {
                    viewColor.setBackgroundColor(Color.parseColor(summary.courseColor))
                } catch (e: Exception) {
                    viewColor.setBackgroundColor(Color.parseColor("#1565C0"))
                }

                btnPresent.setOnClickListener { onPresent(summary) }
                btnAbsent.setOnClickListener { onAbsent(summary) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CourseAttendanceSummary>() {
        override fun areItemsTheSame(old: CourseAttendanceSummary, new: CourseAttendanceSummary) =
            old.courseId == new.courseId
        override fun areContentsTheSame(old: CourseAttendanceSummary, new: CourseAttendanceSummary) =
            old == new
    }
}
