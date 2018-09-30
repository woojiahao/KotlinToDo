package chill.me.kotlintodo.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import chill.me.kotlintodo.EditNote
import chill.me.kotlintodo.R
import chill.me.kotlintodo.models.Note
import chill.me.kotlintodo.states.Priority
import kotlinx.android.synthetic.main.note_card.view.*

class NoteAdapter(private val context: Context, private val notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
	class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
		fun setNoteTitle(title: String) {
			v.noteTitle.text = title
		}

		fun setNoteContent(content: String) {
			v.noteContents.text = content
		}

		fun setNotePriority(priority: Priority) {
			v.notePriorityColorBox.setBackgroundColor(priority.parseColor())
			v.notePriorityText.setTextColor(priority.parseColor())
			v.notePriorityText.text = priority.name
		}

		fun setNoteDueDate(dueDate: String?) {
			v.noteDueDate.text = dueDate ?: "No due date"
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ViewHolder(
			LayoutInflater
				.from(parent.context)
				.inflate(R.layout.note_card, parent, false)
		)

	override fun getItemCount() = notes.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val note = notes[position]
		holder.setNoteTitle(note.title)
		holder.setNoteContent(note.content)
		holder.setNoteDueDate(note.dueDate)
		holder.setNotePriority(note.priority)

		holder.v.setOnClickListener {
			context.startActivity(
				Intent(context, EditNote::class.java)
					.putExtra("note", note)
			)
		}
	}
}