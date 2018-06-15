package com.example.knoxpo.todolistfirestore.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.example.knoxpo.todolistfirestore.Model.ToDoCollection
import com.example.knoxpo.todolistfirestore.Model.ToDoModel
import com.example.knoxpo.todolistfirestore.R
import com.example.knoxpo.todolistfirestore.activity.LoginActivtiy
import com.example.knoxpo.todolistfirestore.adapater.ToDoAdapater
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_todo_list.view.*
import java.util.ArrayList

class ToDoListFragment : Fragment(), ToDoAdapater.OnClickListener {


    var count = 0

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    var mRecyclerView: RecyclerView? = null

    private var mToDoList: ArrayList<ToDoModel>? = null

    var mToDoAdapater: ToDoAdapater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
                ToDoCollection.Fields.TITLE to "Himanshu : - todo11$ : - $count",
                ToDoCollection.Fields.CHECKED to false,
                ToDoCollection.Fields.USERID to  mAuth.currentUser!!.uid
        )
        db.collection(ToDoCollection.NAME).add(map as Map<String, Any>)
        mToDoAdapater?.notifyDataSetChanged()
        count++
    }


    private fun fetchData() {

        db.collection(ToDoCollection.NAME)
                .addSnapshotListener { querySnapshot, _ ->
                    mToDoList?.clear()
                    if (querySnapshot != null) {
                        for (document in querySnapshot.documents) {
                            val mToDoModel = ToDoModel()
                            mToDoModel.id = document.id
                            mToDoModel.title = document.get(ToDoCollection.Fields.TITLE).toString()
                            mToDoModel.isCheck = (document.get(ToDoCollection.Fields.CHECKED) as Boolean)
                            mToDoList?.add(mToDoModel)
                        }
                    }
                    mToDoAdapater?.notifyDataSetChanged()
                }


    }

    override fun onClickUpdate(mToDoModel: String?, check: Boolean?) {


        val ref: DocumentReference = db.collection(ToDoCollection.NAME).document(mToDoModel!!)

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


    override fun onClickDelete(mToDoModel: ToDoModel, adapterPosition: Int) {

        db.collection(ToDoCollection.NAME).document(mToDoModel.id!!).delete()

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.logout,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(activity,LoginActivtiy::class.java)
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }


}