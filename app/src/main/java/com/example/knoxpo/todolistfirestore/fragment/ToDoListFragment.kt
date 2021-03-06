package com.example.knoxpo.todolistfirestore.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.example.knoxpo.todolistfirestore.Model.ToDoCollection
import com.example.knoxpo.todolistfirestore.Model.ToDoModel
import com.example.knoxpo.todolistfirestore.R
import com.example.knoxpo.todolistfirestore.activity.LoginActivtiy
import com.example.knoxpo.todolistfirestore.adapater.ToDoAdapater
import com.example.knoxpo.todolistfirestore.commonfilebysir.TodoListCommonFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_todo_list.*
import java.util.ArrayList

class ToDoListFragment : CommonToDoList(), ToDoAdapater.OnClickListener {

    override val query: Query = FirebaseFirestore
            .getInstance()
            .collection(ToDoCollection.NAME)
            .whereEqualTo(
                    ToDoCollection.Fields.USERID,
                    FirebaseAuth.getInstance().currentUser?.uid
            )


    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val REQUEST_CODE_FOR_DIALOG = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            addData()
        }
    }

    private fun addData() {

        val map: MutableMap<String, Any> = mutableMapOf(
                ToDoCollection.Fields.TITLE to "Himanshu : - todo11$ : - $count",
                ToDoCollection.Fields.CHECKED to false,
                ToDoCollection.Fields.USERID to mAuth.currentUser!!.uid
        )
        db.collection(ToDoCollection.NAME).add(map as Map<String, Any>)
        count++
    }


    override fun onShareButtonClick(id: String?) {

        var dialog = Aleartdialog.newInstance(id)

        var fm = this.fragmentManager

        dialog.show(fm, "dialog")

        dialog.setTargetFragment(this, REQUEST_CODE_FOR_DIALOG)

    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.logout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.logout) {
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(activity, LoginActivtiy::class.java)
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (requestCode == REQUEST_CODE_FOR_DIALOG && resultCode == Activity.RESULT_OK && data != null) {

            val model = data.getStringExtra("id")
            val model_id = data.getStringExtra("modelId")

            var ref: DocumentReference = FirebaseFirestore.getInstance().collection(ToDoCollection.NAME).document(model_id)

            val map : Map<String,Any> = mutableMapOf(
                    "${ToDoCollection.Fields.SHARES}.$model" to  true
            )
            ref.update(map).addOnCompleteListener {

                Toast.makeText(activity, "This Data is now visiable  to $model", Toast.LENGTH_SHORT).show()

            }

        }
    }



}