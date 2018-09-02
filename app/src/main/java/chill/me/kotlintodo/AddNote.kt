package chill.me.kotlintodo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import chill.me.kotlintodo.utility.toast
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNote : AppCompatActivity() {

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
		when(item!!.itemId) {
			R.id.addNoteSetDueDate -> toast("Setting due date")
			R.id.addNoteSetPriority -> toast("Setting priority")
		}
		return true
	}
}
