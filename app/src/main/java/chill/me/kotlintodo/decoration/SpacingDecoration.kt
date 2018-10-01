package chill.me.kotlintodo.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import chill.me.kotlintodo.decoration.Direction.*
import kotlin.math.ceil
import kotlin.math.floor

class SpacingDecoration(
	private val horizontalSpacing: Int,
	private val verticalSpacing: Int,
	private val columnCount: Int
) : RecyclerView.ItemDecoration() {

	init {
		if (columnCount <= 0) throw SpacingDecorationError("Cannot have a column count of 0 or less")
	}

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

		val viewPos = parent.getChildAdapterPosition(view)
		val itemCount = parent.adapter.itemCount
		val rowCount = ceil((itemCount / columnCount).toDouble()).toInt()
		val map = generateMap(viewPos, itemCount, rowCount, columnCount)

		outRect.set(map[Left]!!, map[Top]!!, map[Right]!!, map[Bottom]!!)
	}

	private fun generateMap(viewPos: Int, itemCount: Int, rowCount: Int, columnCount: Int): Map<Direction, Int> {
		val alongLeft = viewPos % columnCount == 0
		val alongRight = viewPos % columnCount == columnCount - 1
		val alongTop = viewPos < columnCount
		val alongBottom = viewPos >= (itemCount - columnCount)

		val topLeft = alongLeft && alongTop
		val topRight = alongRight && alongTop
		val bottomLeft = alongLeft && alongBottom
		val bottomRight = alongRight && alongBottom

		val td = verticalSpacing / 2
		val lr = horizontalSpacing / 2

		var left = 0
		var right = 0
		var top = 0
		var bottom = 0

		if (rowCount == 1 && columnCount != 1) {
			when {
				alongLeft -> right = lr
				alongRight -> left = lr
				else -> {
					left = lr
					right = lr
				}
			}
		} else if (columnCount == 1 && rowCount != 1) {
			when {
				alongTop -> bottom = td
				alongBottom -> top = td
				else -> {
					top = td
					bottom = td
				}
			}
		} else if (rowCount > 1 && columnCount > 1) {
			val curRow = floor((viewPos / columnCount).toDouble()).toInt() + 1
			if (alongBottom) {
				bottom = 4
			}

			if (curRow != rowCount && bottomRight) {
				bottom = verticalSpacing
				top = td
				left = lr
			} else if (curRow == rowCount - 1 && !bottomRight) {
				bottom = verticalSpacing
				top = td
				right = lr
			} else if ((viewPos + 1 == itemCount) && !bottomRight) {
				top = 0
				right = lr
			} else if (topLeft || topRight || bottomLeft || bottomRight) {
				bottom = if (topLeft || topRight) td else 0
				top = if (bottomLeft || bottomRight) td else 0
				right = if (topLeft || bottomLeft) lr else 0
				left = if (topRight || bottomRight) lr else 0
			} else {
				if (alongLeft || alongRight || alongTop || alongBottom) {
					top = if (alongLeft || alongRight) td else 0
					bottom = if (alongLeft || alongRight) td else 0
					left = if (alongTop || alongBottom) lr else 0
					right = if (alongTop || alongBottom) lr else 0

					when {
						alongLeft -> right = lr
						alongRight -> left = lr
						alongTop -> bottom = td
						alongBottom -> {
							top = td
							bottom = 4
						}
					}
				} else {
					top = td
					bottom = td
					left = lr
					right = lr
				}
			}
		}

		return mapOf(
			Left to left,
			Right to right,
			Top to top,
			Bottom to bottom
		)
	}
}