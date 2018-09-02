package chill.me.kotlintodo.utility

import android.graphics.Color

val String.color get() = Color.parseColor("#$this")