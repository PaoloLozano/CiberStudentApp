package com.cibertec.student.presentation.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.student.databinding.ItemNoteBinding
import com.cibertec.student.domain.model.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(
    private val onNoteClick: (Note) -> Unit,
    private val onNoteDelete: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                tvNoteTitle.text = note.title
                tvNoteContent.text = note.content
                tvNoteCourse.text = note.courseName.ifBlank { "General" }

                val sdf = SimpleDateFormat("dd MMM", Locale("es", "PE"))
                tvNoteDate.text = sdf.format(Date(note.updatedAt))

                try {
                    root.setCardBackgroundColor(Color.parseColor(note.color))
                } catch (e: Exception) {
                    root.setCardBackgroundColor(Color.WHITE)
                }

                root.setOnClickListener { onNoteClick(note) }
                root.setOnLongClickListener {
                    onNoteDelete(note)
                    true
                }
            }
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(old: Note, new: Note) = old.id == new.id
        override fun areContentsTheSame(old: Note, new: Note) = old == new
    }
}
