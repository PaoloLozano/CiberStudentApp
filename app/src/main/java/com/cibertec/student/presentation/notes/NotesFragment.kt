package com.cibertec.student.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cibertec.student.databinding.FragmentNotesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
        setupListeners()
        observeState()
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(
            onNoteClick = { note -> showEditNoteDialog(note) },
            onNoteDelete = { note -> viewModel.deleteNote(note) }
        )
        binding.rvNotes.apply {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun setupSearch() {
        binding.searchBar.editText?.addTextChangedListener { text ->
            searchJob?.cancel()
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(300)
                viewModel.search(text?.toString() ?: "")
            }
        }
    }

    private fun setupListeners() {
        binding.fabAddNote.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state.notes.isEmpty() && !state.isLoading) {
                        binding.emptyState.visibility = View.VISIBLE
                        binding.rvNotes.visibility = View.GONE
                    } else {
                        binding.emptyState.visibility = View.GONE
                        binding.rvNotes.visibility = View.VISIBLE
                        noteAdapter.submitList(state.notes)
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

    private fun showAddNoteDialog() {
        val dialog = AddNoteDialog(null) { note ->
            viewModel.addNote(note)
        }
        dialog.show(childFragmentManager, "AddNoteDialog")
    }

    private fun showEditNoteDialog(note: com.cibertec.student.domain.model.Note) {
        val dialog = AddNoteDialog(note) { updatedNote ->
            viewModel.updateNote(updatedNote)
        }
        dialog.show(childFragmentManager, "EditNoteDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
