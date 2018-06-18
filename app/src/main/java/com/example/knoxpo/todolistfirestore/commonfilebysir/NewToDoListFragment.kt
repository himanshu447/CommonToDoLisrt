package com.example.knoxpo.todolistfirestore.commonfilebysir

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.example.knoxpo.todolistfirestore.Model.ToDoCollection
import com.example.knoxpo.todolistfirestore.R
import com.example.knoxpo.todolistfirestore.activity.LoginActivtiy
import com.example.knoxpo.todolistfirestore.fragment.Aleartdialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_todo_list.*

class NewToDoListFragment : TodoListCommonFragment() {

    override val query = FirebaseFirestore
            .getInstance()
            .collection(ToDoCollection.NAME)
            .whereEqualTo(
                    ToDoCollection.Fields.USERID,
                    FirebaseAuth.getInstance().currentUser?.uid
            )

    val REQUEST_CODE_FOR_DIALOG = 123

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.logout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.logout) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivtiy::class.java)
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }

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

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val map: MutableMap<String, Any> = mutableMapOf(
                ToDoCollection.Fields.TITLE to "Himanshu : - todo11$ : - $count++",
                ToDoCollection.Fields.CHECKED to false,
                ToDoCollection.Fields.USERID to uid
        )
        FirebaseFirestore
                .getInstance()
                .collection(ToDoCollection.NAME)
                .add(map as Map<String, Any>)
    }


    override fun onShareButtonClick(id: String?) {

        var dialog = Aleartdialog.newInstance(id)

        var fm = this.fragmentManager

        dialog.show(fm, "dialog")

        dialog.setTargetFragment(this, REQUEST_CODE_FOR_DIALOG)

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