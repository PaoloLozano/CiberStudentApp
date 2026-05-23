package com.cibertec.student.presentation.attendance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cibertec.student.databinding.FragmentAttendanceBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AttendanceFragment : Fragment() {

    private var _binding: FragmentAttendanceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AttendanceViewModel by viewModels()
    private lateinit var attendanceAdapter: AttendanceCourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeState()
    }

    private fun setupRecyclerView() {
        attendanceAdapter = AttendanceCourseAdapter(
            onPresent = { summary ->
                viewModel.markAttendance(
                    summary.courseId,
                    summary.courseName,
                    com.cibertec.student.domain.model.AttendanceRecord.AttendanceStatus.PRESENT
                )
            },
            onAbsent = { summary ->
                viewModel.markAttendance(
                    summary.courseId,
                    summary.courseName,
                    com.cibertec.student.domain.model.AttendanceRecord.AttendanceStatus.ABSENT
                )
            }
        )
        binding.rvAttendance.apply {
            adapter = attendanceAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    // Alert banner
                    binding.cardAlert.visibility = if (state.hasAtRiskCourses) View.VISIBLE else View.GONE

                    // Summary stats
                    binding.tvTotalPresent.text = state.totalPresent.toString()
                    binding.tvTotalAbsent.text = state.totalAbsent.toString()

                    // List
                    if (state.summaries.isEmpty() && !state.isLoading) {
                        binding.emptyState.visibility = View.VISIBLE
                        binding.rvAttendance.visibility = View.GONE
                    } else {
                        binding.emptyState.visibility = View.GONE
                        binding.rvAttendance.visibility = View.VISIBLE
                        attendanceAdapter.submitList(state.summaries)
                    }

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
