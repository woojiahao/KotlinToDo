package chill.me.kotlintodo.utility

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun Context.toast(message: String) = Toast.makeText(this, message, LENGTH_SHORT).show()

fun getCurrentDateTimeFormatted() = DateTimeFormat.forPattern("dd/MM/yyyy").print(DateTime.now())!!

fun getDateTimeFormatted(dateTime: DateTime) = DateTimeFormat.forPattern("dd/MM/yyyy").print(dateTime)!!