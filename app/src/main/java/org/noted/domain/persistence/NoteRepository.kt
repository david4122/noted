package org.noted.domain.persistence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.noted.domain.model.Note

interface NoteRepository {

    fun addNote(note: Note)

    fun getNote(id: String): LiveData<Note>

    fun getNotes(): LiveData<List<MutableLiveData<Note>>>

    fun deleteNote(id: String)

}