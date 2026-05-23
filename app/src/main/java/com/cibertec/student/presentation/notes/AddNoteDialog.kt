package com.cibertec.student.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cibertec.student.databinding.DialogAddNoteBinding
import com.cibertec.student.domain.model.Note
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNoteDialog(
    private val existingNote: Note? = null,
    private val onSave: (Note) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogAddNoteBinding? = null
    private val binding get() = _binding!!

    private val noteColors = listOf(
        "#FFFFFF", "#E3F2FD", "#E8F5E9", "#FFF8E1",
        "#FCE4EC", "#EDE7F6", "#E0F7FA", "#F3E5F5"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pre-fill if editing
        existingNote?.let {
            binding.etTitle.setText(it.title)
            binding.etContent.setText(it.content)
            binding.etCourse.setText(it.courseName)
        }

        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnSave.setOnClickListener { saveNote() }
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString().trim()
        if (title.isBlank()) {
            binding.tilTitle.error = "Ingresa un título"
            return
        }

        val note = Note(
            id = existingNote?.id ?: 0,
            title = title,
            content = binding.etContent.text.toString().trim(),
            courseName = binding.etCourse.text.toString().trim(),
            color = noteColors.random(),
            createdAt = existingNote?.createdAt ?: System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )

        onSave(note)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
