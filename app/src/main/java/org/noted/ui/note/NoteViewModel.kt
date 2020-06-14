package org.noted.ui.note

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import org.noted.domain.livedata.FirestoreNoteLiveData
import org.noted.domain.model.Note
import org.noted.domain.persistence.FirestoreNoteRepository
import java.util.*

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: FirestoreNoteRepository

    init {
        val prefs = getApplication<Application>().getSharedPreferences("preferences", Context.MODE_PRIVATE)
        var userId = prefs.getString("userId", null)

        if(userId == null) {
            userId = UUID.randomUUID().toString()
            with(prefs.edit()) {
                putString("userId", userId)
                commit()
            }
        }

        repo = FirestoreNoteRepository(userId)
    }

    val notes = repo.getNotes()

    val noteContent = MutableLiveData<String>()

    private var _currentNote: MutableLiveData<Note>? = null
    val currentNote: LiveData<Note>?
        get() = _currentNote

    fun select(note: MutableLiveData<Note>?) {
        _currentNote = note
        noteContent.value = note?.value?.content ?: ""
    }

    fun save() {
        if(currentNote?.value == null) {
            val new = repo.addNote(Note(noteContent.value!!))
        } else {
            _currentNote!!.value = Note(_currentNote!!.value!!.id!!, noteContent.value!!)
        }
    }

    fun delete(id: String) {
        repo.deleteNote(id)
    }
}