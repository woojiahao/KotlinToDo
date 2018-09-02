package chill.me.kotlintodo.database

import android.util.Log
import chill.me.kotlintodo.models.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

fun addNoteToDatabase(note: Note) =
	FirebaseDatabase
		.getInstance()
		.reference
		.child("notes").push().setValue(note)

@Suppress("UNCHECKED_CAST")
fun getNotes(onComplete: (List<Note>) -> Unit) =
	FirebaseDatabase
		.getInstance()
		.reference
		.child("notes")
		.addListenerForSingleValueEvent(object: ValueEventListener {
			override fun onCancelled(p0: DatabaseError)= Unit

			override fun onDataChange(p0: DataSnapshot) {
				val notes = mutableListOf<Note>()
				for (snapshot in p0.children) {
					val creationDateMap = snapshot.child("creationDate").value
					Log.d("GET_NOTES", creationDateMap.toString())
					val note = snapshot.getValue(Note::class.java)
					if (note != null) notes.add(note)
				}

				onComplete(notes)
			}

		})