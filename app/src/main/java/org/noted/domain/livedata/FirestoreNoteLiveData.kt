package org.noted.domain.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.firestore.*
import org.noted.domain.model.Note

class FirestoreNoteLiveData: MutableLiveData<Note>, EventListener<DocumentSnapshot>,
    Observer<Note> {

    private var listenerReg: ListenerRegistration? = null

    private var docRef: DocumentReference

    constructor(docRef: DocumentReference) {
        this.docRef = docRef
        docRef.get().addOnSuccessListener {
            value = it.toObject(Note::class.java)
        }

        observeForever(this)
    }

    constructor(docRef: DocumentReference, value: Note) {
        this.docRef = docRef
        this.value = value

        observeForever(this)
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

        } else if(ex != null) {
            throw ex
        } else {
            listenerReg?.remove()
            removeObserver(this)
        }
    }

    override fun onChanged(n: Note?) {
        if(n != null) docRef.set(n)
    }
}