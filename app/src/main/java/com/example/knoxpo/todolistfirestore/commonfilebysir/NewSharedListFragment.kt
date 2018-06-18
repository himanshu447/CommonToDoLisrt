package com.example.knoxpo.todolistfirestore.commonfilebysir

import com.example.knoxpo.todolistfirestore.Model.ToDoCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NewSharedListFragment : TodoListCommonFragment() {
    override val query = FirebaseFirestore
            .getInstance()
            .collection(ToDoCollection.NAME)
            .whereEqualTo(
                    "${ToDoCollection.Fields.SHARES}.${FirebaseAuth.getInstance().currentUser?.uid}",
                    true
            )

    override val isMyList = false


}