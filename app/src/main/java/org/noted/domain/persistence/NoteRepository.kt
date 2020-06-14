package org.noted.domain.persistence

import androidx.lifecycle.LiveData
import org.noted.domain.model.Note

interface NoteRepository {

    fun addNote(note: Note)

    fun getNote(id: String): LiveData<Note>

    fun getNotes(): LiveData<List<Note>>

    fun deleteNote(id: String)

}