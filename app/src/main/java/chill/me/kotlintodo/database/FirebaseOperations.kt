package chill.me.kotlintodo.database

import chill.me.kotlintodo.models.Note
import com.google.firebase.database.FirebaseDatabase

fun addNoteToDatabase(note: Note) =
	FirebaseDatabase
		.getInstance()
		.reference
		.child("notes").push().setValue(note)
