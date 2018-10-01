package chill.me.kotlintodo.states

import android.graphics.Color

enum class Priority(val color: String) {
	Highest("FF1744"),
	High("FF8A80"),
	Medium("FFA726"),
	Low("448AFF"),
	Lowest("69F0AE");

	fun parseColor() = Color.parseColor("#$color")
}