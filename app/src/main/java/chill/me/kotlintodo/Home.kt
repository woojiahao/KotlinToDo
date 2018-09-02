package chill.me.kotlintodo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import chill.me.kotlintodo.adapters.NoteAdapter
import chill.me.kotlintodo.models.Note
import chill.me.kotlintodo.states.Priority
import chill.me.kotlintodo.ui.spacing.SpacingDecoration
import kotlinx.android.synthetic.main.activity_home.*
import org.joda.time.DateTime

class Home : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)

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
		val adapter = NoteAdapter(notes)
		notesList.adapter = adapter
		notesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		notesList.addItemDecoration(SpacingDecoration(0, 32, 1))
	}
}
