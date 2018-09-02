package chill.me.kotlintodo.models

import chill.me.kotlintodo.states.Priority
import chill.me.kotlintodo.states.Priority.Lowest
import chill.me.kotlintodo.utility.getCurrentDateTimeFormatted

data class Note(val title: String,
				val content: String,
				val creationDate: String,
				val dueDate: String? = null,
				val priority: Priority = Lowest) {
	constructor(): this("", "", getCurrentDateTimeFormatted()) {

	}
}