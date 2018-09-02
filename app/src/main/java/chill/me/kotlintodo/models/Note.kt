package chill.me.kotlintodo.models

import chill.me.kotlintodo.states.Priority
import chill.me.kotlintodo.states.Priority.Lowest
import org.joda.time.DateTime

data class Note(val title: String,
				val content: String,
				val creationDate: DateTime,
				val dueDate: DateTime? = null,
				val priority: Priority = Lowest)