package chill.me.kotlintodo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import chill.me.kotlintodo.database.addNoteToDatabase
import chill.me.kotlintodo.database.editNoteInDatabase
import chill.me.kotlintodo.models.Note
import chill.me.kotlintodo.states.Priority
import chill.me.kotlintodo.utility.getCurrentDateTimeFormatted
import kotlinx.android.synthetic.main.activity_edit_note.*
import org.joda.time.DateTime

class EditNote : AppCompatActivity() {
	private var existingNote: Note? = null
	private var dueDate: String? = null
	private var priority = Priority.Lowest

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_edit_note)

		init()
		connectListeners()
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
			android.R.id.home -> NavUtils.navigateUpFromSameTask(this)
			R.id.editNoteSetDueDate -> showDatePicker()
			R.id.editNoteSetPriority -> showPrioritySelection()
		}
		return true
	}

	private fun showDatePicker() {
		val now = DateTime.now()
		DatePickerDialog(
			this,
			{ _, year, month, day ->
				dueDate = "${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/$year"
			},
			now.year, now.monthOfYear, now.dayOfMonth)
			.show()
	}

	private fun showPrioritySelection() {
		AlertDialog
			.Builder(this)
			.setTitle("Select Task Priority")
			.setSingleChoiceItems(
				Priority.values().map { it.name }.toTypedArray(),
				Priority.valueOf(priority.name).ordinal
			) { _, selected ->
				priority = Priority.values()[selected]
			}
			.setPositiveButton("Confirm") { _, _ -> }
			.setCancelable(false)
			.show()
	}

	private fun init() {
		supportActionBar!!.setDisplayHomeAsUpEnabled(true)
		supportActionBar!!.setDisplayShowHomeEnabled(true)
		supportActionBar!!.setDisplayShowTitleEnabled(false)

		val extras = intent.extras
		if (extras != null) {
			existingNote = extras.getParcelable("note")
			dueDate = existingNote!!.dueDate
			priority = existingNote!!.priority
			editNoteTitle.setText(existingNote!!.title)
			editNoteContent.setText(existingNote!!.content)
		}
	}

	private fun connectListeners() {
		confirmNoteFAB.setOnClickListener {
			addNote()
			finish()
		}
	}

	private fun addNote() {
		val title = editNoteTitle.text.toString()
		val content = editNoteContent.text.toString()

		if (title.isEmpty() && content.isEmpty()) return

		val noteToAdd = Note(title, content, getCurrentDateTimeFormatted(), dueDate, priority)
		if (existingNote != null) {
			noteToAdd.noteId = existingNote!!.noteId
			editNoteInDatabase(noteToAdd)
		} else addNoteToDatabase(noteToAdd)
	}
}
