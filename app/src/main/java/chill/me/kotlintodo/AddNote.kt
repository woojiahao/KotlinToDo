package chill.me.kotlintodo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import chill.me.kotlintodo.database.addNoteToDatabase
import chill.me.kotlintodo.models.Note
import chill.me.kotlintodo.states.Priority
import chill.me.kotlintodo.utility.getCurrentDateTimeFormatted
import chill.me.kotlintodo.utility.getDateTimeFormatted
import kotlinx.android.synthetic.main.activity_add_note.*
import org.joda.time.DateTime

class AddNote : AppCompatActivity() {
	private var dueDate: DateTime? = null
	private var priority = Priority.Lowest

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_note)

		init()
		connectListeners()
	}

	private fun init() {
		supportActionBar!!.setDisplayHomeAsUpEnabled(true)
		supportActionBar!!.setDisplayShowHomeEnabled(true)
		supportActionBar!!.setDisplayShowTitleEnabled(false)
	}

	private fun connectListeners() {
		confirmNoteFAB.setOnClickListener {
			addNote()
			finish()
		}
	}

	private fun addNote() {
		val title = addNoteTitle.text.toString()
		val content = addNoteContent.text.toString()

		if (title.isEmpty() && content.isEmpty()) return

		val noteToAdd = Note(
			title,
			content,
			getCurrentDateTimeFormatted(),
			if (dueDate == null) null else getDateTimeFormatted(dueDate!!),
			priority)
		addNoteToDatabase(noteToAdd)
	}

	override fun onSupportNavigateUp(): Boolean {
		onBackPressed()
		return true
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.add_note_menu, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when (item!!.itemId) {
			R.id.addNoteSetDueDate -> {
				val now = DateTime.now()
				var displayYear = now.year
				var displayMonth = now.monthOfYear
				var displayDay = now.dayOfMonth
				if (dueDate != null) {
					displayYear = dueDate!!.year
					displayMonth = dueDate!!.monthOfYear
					displayDay = dueDate!!.dayOfMonth
				}
				DatePickerDialog(
					this, { _, year, month, day -> dueDate = DateTime(year, month, day, 12, 0) },
					displayYear, displayMonth, displayDay).show()
			}
			R.id.addNoteSetPriority -> {
				val dialogBuilder = AlertDialog.Builder(this)
				dialogBuilder.setTitle("Select Task Priority")
				dialogBuilder.setSingleChoiceItems(
					Priority.values().map { it.name }.toTypedArray(),
					Priority.valueOf(priority.name).ordinal
				) { _, selection -> priority = Priority.values()[selection] }
				dialogBuilder.show()
			}
			android.R.id.home -> NavUtils.navigateUpFromSameTask(this)
		}
		return true
	}
}
