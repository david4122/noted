package org.noted

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.noted.dummy.DummyContent

class NoteListFragment : Fragment() {

    private lateinit var viewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        // Set the adapter
        with(view.findViewById<RecyclerView>(R.id.note_list)) {
            layoutManager = LinearLayoutManager(context)
            adapter = NoteRecyclerViewAdapter(viewModel.notes)

            viewModel.notes.observe(viewLifecycleOwner, Observer { this@with.adapter?.notifyDataSetChanged() })
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