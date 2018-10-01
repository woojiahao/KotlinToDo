package chill.me.kotlintodo.database

import chill.me.kotlintodo.models.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

fun addNoteToDatabase(note: Note) {
	val noteNode = FirebaseDatabase.getInstance().reference.child("notes").push()
	note.noteId = noteNode.key
	noteNode.setValue(note)
}

fun editNoteInDatabase(note: Note) =
	FirebaseDatabase.getInstance()
		.reference
		.child("notes")
		.updateChildren(mapOf(note.noteId!! to note))


fun getNotes(onComplete: (List<Note>) -> Unit) =
	FirebaseDatabase.getInstance().reference.child("notes")
		.addListenerForSingleValueEvent(object : ValueEventListener {
			override fun onCancelled(ds: DatabaseError) = Unit

			override fun onDataChange(ds: DataSnapshot) =
				onComplete(ds.children.mapNotNull { it.getValue(Note::class.java) })
		})