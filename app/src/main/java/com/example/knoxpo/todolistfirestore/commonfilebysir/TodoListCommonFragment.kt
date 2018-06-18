package com.example.knoxpo.todolistfirestore.commonfilebysir

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.knoxpo.todolistfirestore.Model.ToDoCollection
import com.example.knoxpo.todolistfirestore.Model.ToDoModel
import com.example.knoxpo.todolistfirestore.R
import com.example.knoxpo.todolistfirestore.adapater.ToDoAdapater
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_todo_list.*

abstract class TodoListCommonFragment: Fragment(), ToDoAdapater.OnClickListener {

    abstract val query: Query

    open val isMyList = true


    private val toDoItems = mutableListOf<ToDoModel>()
    private val toDoAdapater = ToDoAdapater(toDoItems, isMyList)

    val count = toDoItems.size



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = toDoAdapater
        recycler_view.layoutManager = LinearLayoutManager(activity)
        fab.visibility = if(isMyList) View.VISIBLE else View.GONE

        toDoAdapater.onClickListener = this

        fetchData()
    }

    private fun fetchData(){
        query.addSnapshotListener { querySnapshot, _ ->
            toDoItems.clear()
            if (querySnapshot != null) {
                for (document in querySnapshot.documents) {
                    val mToDoModel = ToDoModel()
                    mToDoModel.id = document.id
                    mToDoModel.title = document.get(ToDoCollection.Fields.TITLE).toString()
                    mToDoModel.isCheck = (document.get(ToDoCollection.Fields.CHECKED) as Boolean)
                    toDoItems.add(mToDoModel)
                }
            }
            toDoAdapater.notifyDataSetChanged()
        }
    }

    override fun onClickUpdate(mToDoModel: String?, check: Boolean?) {


        val ref: DocumentReference = FirebaseFirestore.getInstance().collection(ToDoCollection.NAME).document(mToDoModel!!)

        if (check == false) {
            val updateMap: MutableMap<String, Any> = mutableMapOf(
                    ToDoCollection.Fields.CHECKED to true)

            ref.update(updateMap).addOnSuccessListener {
                Toast.makeText(activity, "updated", Toast.LENGTH_SHORT).show()
            }
        } else {
            val updateMap: MutableMap<String, Any> = mutableMapOf(
                    ToDoCollection.Fields.CHECKED to false)

            ref.update(updateMap).addOnSuccessListener {
                Toast.makeText(activity, "updated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onShareButtonClick(id: String?) {

    }
}