package com.example.knoxpo.todolistfirestore.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.example.knoxpo.todolistfirestore.fragment.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivtiy:SingleFragmentActivty() {

    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    var currentuser : FirebaseUser? = null


    override fun onStart() {
        super.onStart()

        currentuser = mAuth.currentUser

        if(currentuser!=null){

            var intent = Intent(this,TabActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun createFragment(): Fragment {
    return LoginFragment()
    }
}