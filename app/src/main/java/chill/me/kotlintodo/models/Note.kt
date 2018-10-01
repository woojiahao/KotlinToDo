package chill.me.kotlintodo.models

import android.os.Parcelable
import chill.me.kotlintodo.states.Priority
import chill.me.kotlintodo.states.Priority.Lowest
import chill.me.kotlintodo.utility.getCurrentDateTimeFormatted
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
	val title: String,
	val content: String,
	val creationDate: String,
	val dueDate: String? = null,
	val priority: Priority = Lowest,
	var noteId: String? = null
) : Parcelable {
	constructor() : this("", "", "", getCurrentDateTimeFormatted())
}