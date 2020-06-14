package org.noted.domain.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import org.noted.domain.model.Note

class FirestoreNoteLiveData: LiveData<Note>, EventListener<DocumentSnapshot> {

    private var listenerReg: ListenerRegistration? = null

    private lateinit var docRef: DocumentReference

    constructor(docRef: DocumentReference) {
        docRef.get().addOnSuccessListener {
            value = it.toObject(Note::class.java)
        }
    }

    constructor(docRef: DocumentReference, value: Note) {
        this.docRef = docRef
        this.value = value
    }

    override fun onActive() {
        super.onActive()
        listenerReg = docRef.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        listenerReg!!.remove()
    }

    override fun onEvent(snap: DocumentSnapshot?, ex: FirebaseFirestoreException?) {
        if(snap != null && snap.exists()) {
            val model = Note(
                snap.id,
                snap.getString("content")!!
            )

            value = model
        } else {
            TODO("Handle error")
        }
    }
}