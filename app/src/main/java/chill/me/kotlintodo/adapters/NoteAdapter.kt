package chill.me.kotlintodo.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import chill.me.kotlintodo.R
import chill.me.kotlintodo.models.Note
import chill.me.kotlintodo.states.Priority
import chill.me.kotlintodo.utility.color
import kotlinx.android.synthetic.main.note_card.view.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class NoteAdapter(val notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
	class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
		fun setNoteTitle(title: String) {
			v.noteTitle.text = title
		}

		fun setNoteContent(content: String) {
			v.noteContents.text = content
		}

		fun setNotePriority(priority: Priority) {
			v.notePriorityColorBox.setBackgroundColor(priority.color.color)
			v.notePriorityText.setTextColor(priority.color.color)
			v.notePriorityText.text = priority.name
		}

		fun setNoteDueDate(dueDate: DateTime?) {
			v.noteDueDate.text =
				if (dueDate != null) DateTimeFormat.forPattern("dd/MM/yyyy").print(dueDate)
				else "No due date"
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
	}
}