package chill.me.kotlintodo.utility

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

val String.color get() = Color.parseColor("#$this")

fun Context.toast(message: String) = Toast.makeText(this, message, LENGTH_SHORT).show()