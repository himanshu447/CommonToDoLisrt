package com.example.knoxpo.todolistfirestore.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.example.knoxpo.todolistfirestore.R
import com.example.knoxpo.todolistfirestore.commonfilebysir.NewSharedListFragment
import com.example.knoxpo.todolistfirestore.commonfilebysir.NewToDoListFragment
import com.example.knoxpo.todolistfirestore.fragment.ShareToDoListFragment
import com.example.knoxpo.todolistfirestore.fragment.ToDoListFragment
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)


        val fragment1 = ToDoListFragment()
        val fragment2 = ShareToDoListFragment()

        //initialize adapter
        val adapter = TabAdapter(supportFragmentManager)

        adapter.addFragment(fragment1, "My Todos")
        adapter.addFragment(fragment2, "Shared")

        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }

    inner class TabAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        private val fragments = ArrayList<Fragment>()
        private val titles = ArrayList<String>()

        fun addFragment(fragment: Fragment, title: String){
            fragments.add(fragment)
            titles.add(title)
        }


        override fun getItem(position: Int) = fragments[position]

        override fun getCount() = fragments.size

        override fun getPageTitle(position: Int) = titles[position]

    }

}