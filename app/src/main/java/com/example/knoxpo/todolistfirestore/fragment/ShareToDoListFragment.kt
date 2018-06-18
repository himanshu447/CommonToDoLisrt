package com.example.knoxpo.todolistfirestore.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.knoxpo.todolistfirestore.Model.ToDoCollection
import com.example.knoxpo.todolistfirestore.Model.ToDoModel
import com.example.knoxpo.todolistfirestore.R
import com.example.knoxpo.todolistfirestore.activity.FetchData
import com.example.knoxpo.todolistfirestore.activity.SingleFragmentActivty
import com.example.knoxpo.todolistfirestore.adapater.ToDoAdapater
import com.example.knoxpo.todolistfirestore.adapater.ToDoShareAdapater
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_share_list.view.*


class ShareToDoListFragment : CommonToDoList() {

    override val query = FirebaseFirestore
            .getInstance()
            .collection(ToDoCollection.NAME)
            .whereEqualTo(
                    "${ToDoCollection.Fields.SHARES}.${FirebaseAuth.getInstance().currentUser?.uid}",
                    true
            )

    override val isMyList = false


}