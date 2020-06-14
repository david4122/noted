package org.noted.ui.notelist

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.noted.R
import org.noted.ui.note.NoteViewModel

class NoteListFragment : Fragment() {

    private val viewModel: NoteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        // Set the adapter
        with(view.findViewById<RecyclerView>(R.id.note_list)) {
            layoutManager = LinearLayoutManager(context)
            adapter = NoteRecyclerViewAdapter(viewModel.notes, viewModel)

            viewModel.notes.observe(viewLifecycleOwner, Observer { this@with.adapter?.notifyDataSetChanged() })

            val removeCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.delete((viewHolder as NoteRecyclerViewAdapter.ViewHolder).note.value!!.id!!)
                    with(this@with.adapter!!) {
                        notifyItemRemoved(viewHolder.adapterPosition)
                    }
                }
            }

            ItemTouchHelper(removeCallback).attachToRecyclerView(this)
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        requireActivity().findViewById<FloatingActionButton>(R.id.fab)!!.isVisible = true
    }

    override fun onStop() {
        super.onStop()

        requireActivity().findViewById<FloatingActionButton>(R.id.fab)!!.isVisible = false
    }
}