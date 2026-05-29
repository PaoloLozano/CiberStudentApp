package com.cibertec.student.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.cibertec.student.databinding.FragmentProfileBinding
import com.cibertec.student.presentation.auth.AuthActivity
import android.content.Intent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeState()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnEditProfile.setOnClickListener {
            toggleEditMode()
        }

        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun toggleEditMode() {
        isEditing = !isEditing
        binding.etName.isEnabled = isEditing
        binding.etCareer.isEnabled = isEditing
        binding.etStudentCode.isEnabled = isEditing
        binding.etCycle.isEnabled = isEditing
        binding.btnEditProfile.text = if (isEditing) "Cancelar" else "Editar"
        binding.btnSaveProfile.visibility = if (isEditing) View.VISIBLE else View.GONE

        if (!isEditing) {
            // Restaurar valores originales al cancelar
            viewModel.uiState.value.user?.let { user ->
                binding.etName.setText(user.name)
                binding.etCareer.setText(user.career)
                binding.etStudentCode.setText(user.studentCode)
                binding.etCycle.setText(user.cycle)
            }
        }
    }

    private fun saveProfile() {
        val name = binding.etName.text?.toString()?.trim() ?: ""
        val career = binding.etCareer.text?.toString()?.trim() ?: ""
        val studentCode = binding.etStudentCode.text?.toString()?.trim() ?: ""
        val cycle = binding.etCycle.text?.toString()?.trim() ?: ""

        if (name.isEmpty()) {
            binding.tilName.error = "El nombre es requerido"
            return
        }
        binding.tilName.error = null

        viewModel.updateProfile(name, career, studentCode, cycle)
        toggleEditMode()
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro que quieres cerrar sesión?")
            .setPositiveButton("Cerrar sesión") { _, _ ->
                viewModel.logout()
                navigateToAuth()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun navigateToAuth() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.user?.let { user ->
                        binding.tvProfileName.text = user.name.ifEmpty { "Estudiante" }
                        binding.tvProfileEmail.text = user.email
                        binding.tvProfileCareer.text = user.career.ifEmpty { "Sin carrera" }
                        binding.etName.setText(user.name)
                        binding.etStudentCode.setText(user.studentCode)
                        binding.etCareer.setText(user.career)
                        binding.etCycle.setText(user.cycle)
                    }

                    binding.tvStatCourses.text = state.totalCourses.toString()
                    binding.tvStatTasks.text = state.completedTasks.toString()
                    binding.tvStatAttendance.text = "${state.attendancePercentage}%"

                    state.successMessage?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                        viewModel.clearMessages()
                    }
                    state.error?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                        viewModel.clearMessages()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
