package org.noted.domain.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.firestore.*
import org.noted.domain.model.Note

class FirestoreNoteListLiveData(colRef: CollectionReference) : LiveData<List<MutableLiveData<Note>>>(),
    EventListener<QuerySnapshot> {

    private var collectionRef: CollectionReference = colRef

    private var listenerReg: ListenerRegistration? = null

    init {
        collectionRef.get().addOnSuccessListener { qs ->
            value = qs.map { FirestoreNoteLiveData(it.reference, it.toObject(Note::class.java)) }
        }
    }

    override fun onActive() {
        super.onActive()
        listenerReg = collectionRef.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        listenerReg!!.remove()
    }

    override fun onEvent(snap: QuerySnapshot?, ex: FirebaseFirestoreException?) {
        if(snap != null) {
            val notes = mutableListOf<FirestoreNoteLiveData>()
            for(note in snap) {
                notes.add(FirestoreNoteLiveData(note.reference, Note(note.id, note.getString("content")!!)))
            }

            value = notes
        } else if(ex != null) {
            throw ex
        }
    }
}