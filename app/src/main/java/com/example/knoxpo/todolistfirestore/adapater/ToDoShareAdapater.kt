package com.example.knoxpo.todolistfirestore.adapater

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.knoxpo.todolistfirestore.Model.ToDoModel
import com.example.knoxpo.todolistfirestore.R
import kotlinx.android.synthetic.main.share_list_row.view.*

class ToDoShareAdapater(var mList : List<ToDoModel>) : RecyclerView.Adapter<ToDoShareAdapater.MyViewHolder>() {

    var mlistener : SetOnClickListener?=null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {

        val view : View = LayoutInflater.from(p0.context).inflate(R.layout.share_list_row,p0,false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {

        var mToDoModel : ToDoModel = mList[p1]

        p0.bindData(mToDoModel)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mTextViewTitle: TextView = itemView.textViewShareTitle
        private val mCheckBox: CheckBox? = itemView.sharecheckBox

        fun bindData(mToDoModel: ToDoModel) {

            mTextViewTitle.text = mToDoModel.title
            mCheckBox?.isChecked= mToDoModel.isCheck!!

            mCheckBox?.setOnClickListener {
                mlistener?.onUpdate(mToDoModel.id,mToDoModel.isCheck)
            }
        }

    }

    interface SetOnClickListener
    {
        fun onUpdate(id: String?, check: Boolean?)
    }
}