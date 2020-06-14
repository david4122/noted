package org.noted.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.noted.domain.livedata.FirestoreNoteLiveData
import org.noted.domain.model.Note
import org.noted.domain.persistence.FirestoreNoteRepository

class NoteViewModel: ViewModel() {

    private val repo: FirestoreNoteRepository = FirestoreNoteRepository()

    val notes = repo.getNotes()
    val noteContent = MutableLiveData<String>()

    private var _currentNote: MutableLiveData<Note>? = null
    val currentNote: LiveData<Note>?
        get() = _currentNote

    fun select(note: MutableLiveData<Note>?) {
        _currentNote = note
    }

    fun save() {
        _currentNote?.value = Note(_currentNote?.value?.id!!, noteContent.value!!)
    }

    fun newNote() {
        repo.addNote(Note(noteContent.value!!))
    }
}