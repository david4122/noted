package org.noted.domain.persistence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import org.noted.domain.livedata.FirestoreNoteListLiveData
import org.noted.domain.livedata.FirestoreNoteLiveData
import org.noted.domain.model.Note

class FirestoreNoteRepository(val userId: String): NoteRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override fun addNote(note: Note): LiveData<Note> {
        val doc = firestore.collection(userId).document()
        note.id = doc.id
        doc.set(note)

        return FirestoreNoteLiveData(doc, note)
    }

    override fun getNote(id: String): LiveData<Note> {
        return FirestoreNoteLiveData(firestore.collection(userId).document(id))
    }

    override fun getNotes(): LiveData<List<MutableLiveData<Note>>> {
        return FirestoreNoteListLiveData(firestore.collection(userId))
    }

    override fun deleteNote(id: String) {
        firestore.collection(userId).document(id).delete()
    }

}