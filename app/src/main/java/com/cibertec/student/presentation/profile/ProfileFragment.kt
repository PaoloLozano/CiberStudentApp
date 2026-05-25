package com.cibertec.student.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cibertec.student.R
import com.cibertec.student.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProfileBinding.bind(view)

        mostrarDatosUsuario()
    }

    private fun mostrarDatosUsuario() {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val nombre = user.displayName ?: user.email ?: "Usuario"
            binding.tvTitulo.text = nombre
        } else {
            binding.tvTitulo.text = "No hay usuario"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}