package com.cibertec.student.presentation.schedule

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
import com.cibertec.student.R
import com.cibertec.student.databinding.FragmentScheduleBinding
import com.cibertec.student.domain.model.Course
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScheduleViewModel by viewModels()
    private lateinit var courseAdapter: CourseAdapter

    private val dayNames = listOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb")
    private val dayValues = listOf(1, 2, 3, 4, 5, 6, 7)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
        setupRecyclerView()
        setupListeners()
        observeState()
    }

    private fun setupTabs() {
        dayNames.forEach { day ->
            binding.tabDays.addTab(binding.tabDays.newTab().setText(day))
        }
        // Default to Monday (index 1)
        binding.tabDays.selectTab(binding.tabDays.getTabAt(1))

        binding.tabDays.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.selectDay(dayValues[tab.position])
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupRecyclerView() {
        courseAdapter = CourseAdapter(
            onCourseClick = { course -> /* show detail */ },
            onCourseDelete = { course -> viewModel.deleteCourse(course) }
        )
        binding.rvCourses.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupListeners() {
        binding.fabAddCourse.setOnClickListener {
            showAddCourseDialog()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val displayCourses = if (state.selectedDayCourses.isEmpty())
                        state.courses else state.selectedDayCourses

                    if (state.courses.isEmpty()) {
                        binding.emptyState.visibility = View.VISIBLE
                        binding.rvCourses.visibility = View.GONE
                    } else {
                        binding.emptyState.visibility = View.GONE
                        binding.rvCourses.visibility = View.VISIBLE
                    }

                    courseAdapter.submitList(displayCourses)
                    binding.tvCoursesCount.text = "${state.courses.size} cursos"

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

    private fun showAddCourseDialog() {
        val dialog = AddCourseDialog { course ->
            viewModel.addCourse(course)
        }
        dialog.show(childFragmentManager, "AddCourseDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
