package org.noted.ui.notelist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_note_item.view.*
import org.noted.R
import org.noted.domain.model.Note

import org.noted.dummy.DummyContent.DummyItem
import org.noted.ui.note.NoteViewModel

class NoteRecyclerViewAdapter(
    private val values: LiveData<List<MutableLiveData<Note>>>,
    private val viewModel: NoteViewModel
) : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_note_item, parent, false)

        val holder = ViewHolder(view)

        view.setOnClickListener {
            viewModel.noteContent.value = it.content.text.toString()
            viewModel.select(holder.note)

            it.findNavController().navigate(R.id.action_noteListFragment_to_noteFragment)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values.value!![position]
        holder.note = item
        holder.contentView.text = item.value?.content ?: "..."
    }

    override fun getItemCount(): Int = values.value?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var note: MutableLiveData<Note>
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}