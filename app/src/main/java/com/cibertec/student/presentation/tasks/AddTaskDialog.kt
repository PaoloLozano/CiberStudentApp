package com.cibertec.student.presentation.tasks

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cibertec.student.databinding.DialogAddTaskBinding
import com.cibertec.student.domain.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class AddTaskDialog(
    private val onSave: (Task) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogAddTaskBinding? = null
    private val binding get() = _binding!!

    private var selectedDueDate: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDueDate.setOnClickListener { showDatePicker() }
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnSave.setOnClickListener { saveTask() }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val cal = Calendar.getInstance().apply { set(year, month, day, 23, 59, 0) }
                selectedDueDate = cal.timeInMillis
                val sdf = SimpleDateFormat("EEE, dd MMM yyyy", Locale("es", "PE"))
                binding.etDueDate.setText(sdf.format(Date(selectedDueDate)))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis()
        }.show()
    }

    private fun saveTask() {
        val title = binding.etTitle.text.toString().trim()
        if (title.isBlank()) {
            binding.tilTitle.error = "Ingresa el título de la tarea"
            return
        }

        val priority = when (binding.chipGroupPriority.checkedChipId) {
            binding.chipHigh.id -> Task.Priority.HIGH
            binding.chipLow.id -> Task.Priority.LOW
            else -> Task.Priority.MEDIUM
        }

        val task = Task(
            title = title,
            description = binding.etDescription.text.toString().trim(),
            courseName = binding.etCourse.text.toString().trim(),
            dueDate = selectedDueDate,
            priority = priority,
            status = Task.TaskStatus.PENDING,
            reminderEnabled = binding.switchReminder.isChecked
        )

        onSave(task)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
