package com.cibertec.student.presentation.schedule

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cibertec.student.databinding.DialogAddCourseBinding
import com.cibertec.student.domain.model.Course
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class AddCourseDialog(
    private val onSave: (Course) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogAddCourseBinding? = null
    private val binding get() = _binding!!

    private val courseColors = listOf(
        "#1565C0", "#6A1B9A", "#2E7D32", "#E65100",
        "#00695C", "#C62828", "#283593", "#AD1457"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etStartTime.setOnClickListener { showTimePicker(true) }
        binding.etEndTime.setOnClickListener { showTimePicker(false) }

        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnSave.setOnClickListener { saveCourse() }
    }

    private fun showTimePicker(isStart: Boolean) {
        val picker = TimePickerDialog(requireContext(), { _, hour, minute ->
            val time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
            if (isStart) binding.etStartTime.setText(time)
            else binding.etEndTime.setText(time)
        }, 7, 0, true)
        picker.show()
    }

    private fun saveCourse() {
        val name = binding.etCourseName.text.toString().trim()
        val teacher = binding.etTeacher.text.toString().trim()
        val classroom = binding.etClassroom.text.toString().trim()
        val startTime = binding.etStartTime.text.toString().trim()
        val endTime = binding.etEndTime.text.toString().trim()
        val credits = binding.etCredits.text.toString().toIntOrNull() ?: 0

        if (name.isBlank()) {
            binding.tilCourseName.error = "Ingresa el nombre del curso"
            return
        }

        val selectedDays = mutableListOf<Int>()
        if (binding.chipMon.isChecked) selectedDays.add(2)
        if (binding.chipTue.isChecked) selectedDays.add(3)
        if (binding.chipWed.isChecked) selectedDays.add(4)
        if (binding.chipThu.isChecked) selectedDays.add(5)
        if (binding.chipFri.isChecked) selectedDays.add(6)
        if (binding.chipSat.isChecked) selectedDays.add(7)

        val color = courseColors.random()

        val course = Course(
            name = name,
            teacher = teacher,
            classroom = classroom,
            startTime = startTime.ifBlank { "07:00" },
            endTime = endTime.ifBlank { "09:00" },
            days = selectedDays,
            credits = credits,
            color = color
        )

        onSave(course)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
