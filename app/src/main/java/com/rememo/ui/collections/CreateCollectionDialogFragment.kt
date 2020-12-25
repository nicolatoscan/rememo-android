package com.rememo.ui.collections

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.rememo.R
import com.rememo.models.Collection
import com.rememo.services.api.CollectionServices

class CreateCollectionDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater


            val view = inflater.inflate(R.layout.create_collection_dialog_fragment, null)
            val btnCreate = view.findViewById<Button>(R.id.btn_create)
            val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
            val txtEditName = view.findViewById<EditText>(R.id.txt_edit_name)
            val txtEditDescription = view.findViewById<EditText>(R.id.txt_edit_description)

            btnCreate.setOnClickListener {
                val name = txtEditName.text.toString()
                val description = txtEditDescription.text.toString()

                val c = Collection(name = name, description = description)
                CollectionServices.createCollection(c, onResult = {
                    (parentFragment as CollectionsFragment).addCollection(c)
                    dismiss()
                }, onError = {
                    Toast.makeText(context, "Error Creating new collection, Please try again later", Toast.LENGTH_LONG).show()
                    dismiss()
                })
                btnCreate.isEnabled = false
            }
            btnCancel.setOnClickListener { dismiss() }


            builder.setView(view)
                .setTitle("Create a new collection")
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}