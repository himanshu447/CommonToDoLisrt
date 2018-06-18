package com.example.knoxpo.todolistfirestore.fragment

import android.annotation.SuppressLint
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

abstract class CommonToDoList : Fragment(),ToDoAdapater.OnClickListener {

    abstract val query : Query

    open val isMyList = true


    var madapater: ToDoAdapater? = null

    private val mList= mutableListOf<ToDoModel>()

    var count = mList.size

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_todo_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        madapater = ToDoAdapater(mList,isMyList)

        recycler_view.layoutManager = LinearLayoutManager(activity)

        recycler_view.adapter = madapater

        fab.visibility = if (isMyList) View.VISIBLE else View.GONE

        madapater!!.onClickListener = this

        fatchData()
    }

    private fun fatchData() {

        FirebaseFirestore.getInstance().collection(ToDoCollection.NAME)
                query.addSnapshotListener { querySnapshot, _ ->
                    mList.clear()
                    if (querySnapshot != null) {
                        for (document in querySnapshot.documents) {
                            var model = ToDoModel()
                            model.id = document.id
                            model.title = document.get(ToDoCollection.Fields.TITLE).toString()
                            model.isCheck = document.get(ToDoCollection.Fields.CHECKED) as Boolean
                            mList.add(model)
                        }
                    }
                    madapater?.notifyDataSetChanged()

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}