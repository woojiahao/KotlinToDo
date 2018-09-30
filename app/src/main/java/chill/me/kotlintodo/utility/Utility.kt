package chill.me.kotlintodo.utility

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun getCurrentDateTimeFormatted() = DateTimeFormat.forPattern("dd/MM/yyyy").print(DateTime.now())!!