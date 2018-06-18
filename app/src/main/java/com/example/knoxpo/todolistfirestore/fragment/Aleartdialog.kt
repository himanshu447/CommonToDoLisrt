package com.example.knoxpo.todolistfirestore.fragment

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.knoxpo.todolistfirestore.R
import kotlinx.android.synthetic.main.aleart_dialog.*
import kotlinx.android.synthetic.main.aleart_dialog.view.*

class Aleartdialog : DialogFragment() {


    companion object {
        fun newInstance(id: String?): DialogFragment {

            var b = Bundle()
            b.putString("id", id)

            var dialog = Aleartdialog()
            dialog.arguments = b
            return dialog
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var model_id = arguments?.getString("id")

        var view: View = LayoutInflater.from(activity).inflate(R.layout.aleart_dialog, null)

        var ed: EditText= view.textviewAleartDialog

        var id = ed.text

        return AlertDialog.Builder(activity!!)
                .setView(view)
                .setTitle("share with")
                .setMessage("Do You want to share this item")
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i -> dismiss() })
                .setPositiveButton("yes", DialogInterface.OnClickListener { dialogInterface, i ->

                    val intent = Intent()
                    intent.putExtra("modelId",model_id)
                    intent.putExtra("id",id.toString())

                    targetFragment?.onActivityResult(targetRequestCode,Activity.RESULT_OK,intent)
                    dismiss()
                })
                .create()
    }
}