package com.example.knoxpo.todolistfirestore.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.knoxpo.todolistfirestore.R
import com.example.knoxpo.todolistfirestore.activity.ToDoListAcitivty
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.login.view.*

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var mAuthor : FirebaseAuth = FirebaseAuth.getInstance()

        val view : View = inflater.inflate(R.layout.login,container,false)

        var editTextemail:EditText=view.editTextemail
        var editTextpassword:EditText=view.editTextpassword
        var buttonLogin : Button=view.buttonLogin

        buttonLogin.setOnClickListener {

           // var mAuth : FirebaseUser = mAuthor.currentUser!!


            mAuthor.signInWithEmailAndPassword(editTextemail.text.toString(),editTextpassword.text.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful)
                        {
                            Toast.makeText(activity,"succesffuly login",Toast.LENGTH_SHORT).show()
                            var intent = Intent(this.activity,ToDoListAcitivty::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(activity,"fail to login",Toast.LENGTH_SHORT).show()

                        }
                    }

        }

        return view
    }
}