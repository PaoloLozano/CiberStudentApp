package com.cibertec.student.presentation.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.cibertec.student.R
import com.cibertec.student.core.utils.DateUtils
import com.cibertec.student.databinding.FragmentHomeBinding
import com.cibertec.student.presentation.schedule.CourseAdapter
import com.cibertec.student.presentation.tasks.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var todayCourseAdapter: TodayCourseAdapter
    private lateinit var pendingTaskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupListeners()
        observeState()
        updateDateChip()
    }

    private fun setupRecyclerViews() {
        todayCourseAdapter = TodayCourseAdapter()
        binding.rvTodaySchedule.apply {
            adapter = todayCourseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        pendingTaskAdapter = TaskAdapter(
            onTaskClick = { /* navigate to task detail */ },
            onCheckClick = { task ->
                // Mark as complete handled by tasks fragment
            }
        )
        binding.rvPendingTasks.apply {
            adapter = pendingTaskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupListeners() {
        binding.tvSeeAllSchedule.setOnClickListener {
            findNavController().navigate(R.id.scheduleFragment)
        }
        binding.tvSeeAllTasks.setOnClickListener {
            findNavController().navigate(R.id.tasksFragment)
        }
        binding.ivAvatar.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.user?.let { user ->
                        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                        val greeting = when {
                            hour < 12 -> "¡Buenos días,"
                            hour < 19 -> "¡Buenas tardes,"
                            else -> "¡Buenas noches,"
                        }
                        binding.tvGreeting.text = greeting
                        binding.tvUserName.text = "${user.name.split(" ").firstOrNull() ?: "Estudiante"}!"
                    }

                    binding.tvStatCourses.text = state.totalCourses.toString()
                    binding.tvStatTasks.text = state.pendingTaskCount.toString()

                    if (state.todayCourses.isEmpty()) {
                        binding.tvNoClasses.visibility = View.VISIBLE
                        binding.rvTodaySchedule.visibility = View.GONE
                    } else {
                        binding.tvNoClasses.visibility = View.GONE
                        binding.rvTodaySchedule.visibility = View.VISIBLE
                        todayCourseAdapter.submitList(state.todayCourses)
                    }

                    if (state.pendingTasks.isEmpty()) {
                        binding.tvNoTasks.visibility = View.VISIBLE
                        binding.rvPendingTasks.visibility = View.GONE
                    } else {
                        binding.tvNoTasks.visibility = View.GONE
                        binding.rvPendingTasks.visibility = View.VISIBLE
                        pendingTaskAdapter.submitList(state.pendingTasks)
                    }
                }
            }
        }
    }

    private fun updateDateChip() {
        binding.tvTodayDate.text = DateUtils.formatTodayFull()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
