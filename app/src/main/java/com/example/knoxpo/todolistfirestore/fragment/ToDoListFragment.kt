package com.example.knoxpo.todolistfirestore.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.knoxpo.todolistfirestore.Model.ToDoCollection
import com.example.knoxpo.todolistfirestore.Model.ToDoModel
import com.example.knoxpo.todolistfirestore.R
import com.example.knoxpo.todolistfirestore.adapater.ToDoAdapater
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_todo_list.view.*
import java.util.ArrayList

class ToDoListFragment : Fragment(), ToDoAdapater.OnClickListener {


    var count = 0

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var mRecyclerView: RecyclerView? = null

    private var mToDoList: ArrayList<ToDoModel>? = null

    var mToDoAdapater: ToDoAdapater? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.fragment_todo_list, container, false)

        mRecyclerView = view.recycler_view

        intilation()

        fetchData()

        view.fab.setOnClickListener {

            addData(db, mToDoAdapater)

        }

        return view
    }


    private fun intilation() {

        mToDoList = ArrayList()

        mToDoAdapater = ToDoAdapater(mToDoList!!)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.activity)

        mRecyclerView?.layoutManager = mLayoutManager

        mRecyclerView?.adapter = mToDoAdapater

        mToDoAdapater?.onClickListener = this

    }


    private fun addData(db: FirebaseFirestore, mToDoAdapater: ToDoAdapater?) {

        val map: MutableMap<String, Any> = mutableMapOf(
                ToDoCollection.Fields.TITLE to "todo$$count",
                ToDoCollection.Fields.CHECKED to false
        )
        db.collection(ToDoCollection.NAME).add(map as Map<String, Any>)
        mToDoAdapater?.notifyDataSetChanged()
        count++
    }


    private fun fetchData() {

        db.collection(ToDoCollection.NAME)
                .orderBy(ToDoCollection.Fields.TITLE)
                .addSnapshotListener { querySnapshot, _ ->
                    mToDoList?.clear()
                    if (querySnapshot != null) {
                        for (document in querySnapshot.documents) {
                            val mToDoModel = ToDoModel()
                            mToDoModel.id=document.id
                            mToDoModel.title = document.get(ToDoCollection.Fields.TITLE).toString()
                            mToDoModel.isCheck = (document.get(ToDoCollection.Fields.CHECKED) as Boolean)
                            mToDoList?.add(mToDoModel)
                        }
                    }
                    mToDoAdapater?.notifyDataSetChanged()
                }


    }

    override fun onClickUpdate(mToDoModel: ToDoModel, postion: Int) {


        val ref: DocumentReference = db.collection(ToDoCollection.NAME).document(mToDoModel.id!!)
        val updateMap: MutableMap<String, Any> = mutableMapOf(
                ToDoCollection.Fields.TITLE to "update ",
                ToDoCollection.Fields.CHECKED to true
        )
        ref.update(updateMap).addOnSuccessListener {
            Toast.makeText(activity, "updated", Toast.LENGTH_SHORT).show()
        }
        mToDoAdapater?.notifyItemChanged(postion)

    }

    override fun onClickDelete(mToDoModel: ToDoModel, adapterPosition: Int) {

        db.collection(ToDoCollection.NAME).document(mToDoModel.id!!).delete()

    }


}