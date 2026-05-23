package com.cibertec.student.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.cibertec.student.R
import com.cibertec.student.databinding.FragmentRegisterBinding
import com.cibertec.student.presentation.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    private val careers = listOf(
        "Administración de Empresas",
        "Administración de Negocios Internacionales",
        "Contabilidad",
        "Marketing",
        "Computación e Informática",
        "Diseño Gráfico",
        "Ingeniería de Sistemas",
        "Ingeniería Civil",
        "Ingeniería Industrial",
        "Administración y Gestión Comercial",
        "Otro"
    )

    private val cycles = (1..10).map { "Ciclo $it" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDropdowns()
        setupListeners()
        observeState()
    }

    private fun setupDropdowns() {
        val careerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, careers)
        binding.etCareer.setAdapter(careerAdapter)

        val cycleAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, cycles)
        binding.etCycle.setAdapter(cycleAdapter)
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRegister.setOnClickListener {
            clearErrors()
            val name = binding.etName.text.toString().trim()
            val studentCode = binding.etStudentCode.text.toString().trim()
            val career = binding.etCareer.text.toString().trim()
            val cycle = binding.etCycle.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            viewModel.register(name, studentCode, career, cycle, email, password, confirmPassword)
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progressRegister.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    binding.btnRegister.isEnabled = !state.isLoading

                    if (state.isSuccess) {
                        navigateToMain()
                    }

                    state.error?.let { error ->
                        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
                        viewModel.clearError()
                    }
                }
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun clearErrors() {
        binding.tilName.error = null
        binding.tilEmail.error = null
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
