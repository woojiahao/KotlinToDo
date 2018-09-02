package chill.me.kotlintodo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import chill.me.kotlintodo.adapters.NoteAdapter
import chill.me.kotlintodo.models.Note
import chill.me.kotlintodo.states.Priority
import chill.me.kotlintodo.ui.spacing.SpacingDecoration
import kotlinx.android.synthetic.main.activity_home.*
import org.joda.time.DateTime

class Home : AppCompatActivity() {
	private val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)

		init()
		connectListeners()
	}

	private fun init() {
		val notes = listOf(
			Note(
				"Lorem Ipsum",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc semper rhoncus augue non egestas. Maecenas maximus bibendum pretium.",
				DateTime.now(),
				DateTime.now().plusMonths(1),
				Priority.High
			),
			Note(
				"Lorem Ipsum",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc semper rhoncus augue non egestas. Maecenas maximus bibendum pretium.",
				DateTime.now(),
				DateTime.now().plusMonths(5)
			),
			Note(
				"Lorem Ipsum",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc semper rhoncus augue non egestas. Maecenas maximus bibendum pretium.",
				DateTime.now(),
				DateTime.now().plusMonths(2)
			),
			Note(
				"Lorem Ipsum",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc semper rhoncus augue non egestas. Maecenas maximus bibendum pretium.",
				DateTime.now(),
				DateTime.now().plusMonths(2)
			),
			Note(
				"Lorem Ipsum",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc semper rhoncus augue non egestas. Maecenas maximus bibendum pretium.",
				DateTime.now(),
				DateTime.now().plusMonths(2)
			),
			Note(
				"Lorem Ipsum",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc semper rhoncus augue non egestas. Maecenas maximus bibendum pretium.",
				DateTime.now(),
				DateTime.now().plusMonths(2)
			)
		)

		notesList.adapter = NoteAdapter(notes)
		notesList.layoutManager = linearLayoutManager
		notesList.addItemDecoration(SpacingDecoration(0, 32, 1))
	}

	private fun connectListeners() {
		addNoteFAB.setOnClickListener { startActivity(Intent(this, AddNote::class.java)) }

		notesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			var lastFirstVisibleItem = 0
			override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
				super.onScrolled(recyclerView, dx, dy)
				when {
					(dy > 0 && addNoteFAB.visibility == View.VISIBLE) -> addNoteFAB.hide()
					(dy < 0 && addNoteFAB.visibility != View.VISIBLE) -> addNoteFAB.show()
				}

				val currentFirstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

				when {
					(currentFirstVisibleItem > lastFirstVisibleItem) -> supportActionBar!!.hide()
					(currentFirstVisibleItem < lastFirstVisibleItem) -> supportActionBar!!.show()
				}

				lastFirstVisibleItem = currentFirstVisibleItem;
			}
		})
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.home_menu, menu)
		return true
	}
}
