package com.example.knoxpo.todolistfirestore.activity

import android.support.v4.app.Fragment
import android.view.Menu
import com.example.knoxpo.todolistfirestore.fragment.ToDoListFragment

class ToDoListAcitivty : SingleFragmentActivty() {


    override fun createFragment(): Fragment {

        return ToDoListFragment()
    }


}
