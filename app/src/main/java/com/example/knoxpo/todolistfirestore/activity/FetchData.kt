package com.example.knoxpo.todolistfirestore.activity

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.knoxpo.todolistfirestore.Model.ToDoCollection
import com.example.knoxpo.todolistfirestore.Model.ToDoModel
import com.example.knoxpo.todolistfirestore.adapater.ToDoAdapater
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

 class FetchData() : AppCompatActivity() {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    var mList: ArrayList<ToDoModel> = ArrayList()

     var mToDoAdapater: ToDoAdapater? = null


     fun fatchdata() {


        db.collection(ToDoCollection.NAME)
                .whereEqualTo(ToDoCollection.Fields.USERID, mAuth.currentUser?.uid)
                .addSnapshotListener { querySnapshot, _ ->
                    mList.clear()
                    if (querySnapshot != null) {
                        for (document in querySnapshot.documents) {
                            var mModel = ToDoModel()
                            mModel.id = document.id
                            mModel.title = document.get(ToDoCollection.Fields.TITLE).toString()
                            mModel.isCheck = document.get(ToDoCollection.Fields.CHECKED) as Boolean
                            mList.add(mModel)
                        }

                       /* mToDoAdapater = ToDoAdapater(mList!!)

                        mToDoAdapater?.notifyDataSetChanged()

                        Toast.makeText(this, "list is$mList",Toast.LENGTH_SHORT).show()
*/
                    }


                }


    }

}