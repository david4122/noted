package org.noted.domain.persistence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import org.noted.domain.livedata.FirestoreNoteLiveData
import org.noted.domain.model.Note

const val collectionName = "notes"

class FirestoreNoteRepository: NoteRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override fun addNote(note: Note) {
        val doc = firestore.collection(collectionName).document()
        note.id = doc.id
        doc.set(note)
    }

    override fun getNote(id: String): LiveData<Note> {
        return FirestoreNoteLiveData(firestore.collection(collectionName).document(id))
    }

    override fun getNotes(): LiveData<List<MutableLiveData<Note>>> {
        val notes = MutableLiveData<List<MutableLiveData<Note>>>()
        firestore.collection(collectionName).get().addOnSuccessListener { docs ->
            notes.value = docs.map {
                FirestoreNoteLiveData(it.reference, Note().apply {
                    id = it.id
                    content = it.getString("content")!!
                })
            }
        }
        return notes
    }

    override fun deleteNote(id: String) {
        firestore.collection(collectionName).document(id).delete()
    }

}