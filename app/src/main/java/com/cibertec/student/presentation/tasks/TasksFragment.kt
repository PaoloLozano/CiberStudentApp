package com.cibertec.student.presentation.tasks

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
import com.cibertec.student.databinding.FragmentTasksBinding
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TasksViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupChipFilters()
        setupListeners()
        observeState()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskClick = { task -> showTaskDetail(task) },
            onCheckClick = { task -> viewModel.markComplete(task) },
            onDeleteClick = { task -> viewModel.deleteTask(task) }
        )
        binding.rvTasks.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupChipFilters() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, _ ->
            when (group.checkedChipId) {
                binding.chipAll.id -> viewModel.applyFilter(FilterType.ALL)
                binding.chipPending.id -> viewModel.applyFilter(FilterType.PENDING)
                binding.chipInProgress.id -> viewModel.applyFilter(FilterType.IN_PROGRESS)
                binding.chipDone.id -> viewModel.applyFilter(FilterType.COMPLETED)
            }
        }
    }

    private fun setupListeners() {
        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state.filteredTasks.isEmpty() && !state.isLoading) {
                        binding.emptyState.visibility = View.VISIBLE
                        binding.rvTasks.visibility = View.GONE
                    } else {
                        binding.emptyState.visibility = View.GONE
                        binding.rvTasks.visibility = View.VISIBLE
                        taskAdapter.submitList(state.filteredTasks)
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

    private fun showAddTaskDialog() {
        val dialog = AddTaskDialog { task ->
            viewModel.addTask(task)
        }
        dialog.show(childFragmentManager, "AddTaskDialog")
    }

    private fun showTaskDetail(task: com.cibertec.student.domain.model.Task) {
        // Could open detail dialog or navigate
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
