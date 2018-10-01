package chill.me.kotlintodo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import chill.me.kotlintodo.adapters.NoteAdapter
import chill.me.kotlintodo.database.getNotes
import chill.me.kotlintodo.models.Note
import chill.me.kotlintodo.states.FilterType
import chill.me.kotlintodo.states.FilterType.*
import chill.me.kotlintodo.states.Priority
import chill.me.kotlintodo.ui.spacing.SpacingDecoration
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
	private val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
	private val notes = mutableListOf<Note>()
	private val allNotes = mutableListOf<Note>()
	private val noteAdapter = NoteAdapter(this, notes)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)

		init()
		connectListeners()
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.home_menu, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when (item?.itemId) {
			R.id.homeFilterNotes -> showFilterMenu()
		}
		return true
	}

	override fun onResume() {
		super.onResume()
		loadNotes()
	}

	private fun showFilterMenu() {
		val popupMenu = PopupMenu(this, findViewById<View>(R.id.homeFilterNotes))
		popupMenu.menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)
		popupMenu.setOnMenuItemClickListener { selectedItem ->
			val filterType = when (selectedItem.itemId) {
				R.id.filterHighestPriority -> Highest
				R.id.filterHighPriority -> High
				R.id.filterMediumPriority -> Medium
				R.id.filterLowPriority -> Low
				R.id.filterLowestPriority -> Lowest
				R.id.filterNameAscending -> Ascending
				R.id.filterNameDescending -> Descending
				else -> None
			}
			filterNotes(filterType)
			true
		}
		popupMenu.show()
	}

	private fun init() {
		notesList.addItemDecoration(SpacingDecoration(0, 32, 1))
		loadNotes()
	}

	private fun connectListeners() {
		addNoteFAB.setOnClickListener { startActivity(Intent(this, EditNote::class.java)) }

		notesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
				super.onScrolled(recyclerView, dx, dy)
				when {
					(dy > 0 && addNoteFAB.visibility == View.VISIBLE) -> addNoteFAB.hide()
					(dy < 0 && addNoteFAB.visibility != View.VISIBLE) -> addNoteFAB.show()
				}
			}
		})
	}

	private fun loadNotes() {
		if (loadingScreen.visibility != View.VISIBLE) loadingScreen.visibility = View.VISIBLE
		getNotes {
			if (loadingScreen.visibility == View.VISIBLE) loadingScreen.visibility = View.GONE
			if (it.isEmpty()) {
				noNotesSavedMessage.visibility = View.VISIBLE
				notesList.visibility = View.GONE
			} else {
				noNotesSavedMessage.visibility = View.GONE
				notesList.visibility = View.VISIBLE
				allNotes.clear()
				allNotes.addAll(it)
				refreshNotes(it)
				notesList.adapter = noteAdapter
				notesList.layoutManager = linearLayoutManager
			}
		}
	}

	private fun filterNotes(filterType: FilterType) {
		refreshNotes(allNotes)
		when (filterType) {
			Ascending -> notes.sortBy { it.title }
			Descending -> notes.sortByDescending { it.title }
			Highest, High, Medium, Low, Lowest ->
				refreshNotes(notes.filter { it.priority == Priority.valueOf(filterType.name) })
			None -> notes.sortBy { it.title }
		}
		noteAdapter.notifyDataSetChanged()
	}

	private fun refreshNotes(newNotes: List<Note>) {
		notes.clear()
		notes.addAll(newNotes)
	}
}
