package com.example.knoxpo.todolistfirestore.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.knoxpo.todolistfirestore.R

abstract class SingleFragmentActivty : AppCompatActivity() {

    abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)

        var fm: FragmentManager = supportFragmentManager

        var esxtFragment: Fragment? = fm.findFragmentById(R.id.frame_container)

        if (esxtFragment == null) {
            fm.beginTransaction()
                    .replace(R.id.frame_container, createFragment())
                    .commit()
        }
    }
}