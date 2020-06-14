package org.noted

import androidx.lifecycle.ViewModel
import org.noted.domain.persistence.FirestoreNoteRepository

class NoteListViewModel: ViewModel() {

    private val repo: FirestoreNoteRepository = FirestoreNoteRepository()

    val notes = repo.getNotes()
}