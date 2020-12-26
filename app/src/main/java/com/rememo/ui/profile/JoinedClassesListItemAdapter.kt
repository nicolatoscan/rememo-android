package com.rememo.ui.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rememo.R
import com.rememo.models.StudyClass
import com.rememo.services.api.ClassServices
import com.rememo.services.api.CollectionServices
import com.rememo.ui.collections.CollectionDetailsActivity

class JoinedClassesListItemAdapter(private val itemList: ArrayList<StudyClass>): RecyclerView.Adapter<JoinedClassesListItemAdapter.JoinedClassesListItemViewHolder>() {

    class JoinedClassesListItemViewHolder(itemView: View, private val adapter: JoinedClassesListItemAdapter): RecyclerView.ViewHolder(itemView) {
        var context: Context? = null
        var index: Int? = null
        var studyClass: StudyClass? = null
        val nameTxt: TextView = itemView.findViewById(R.id.txt_title)

        init {
            context = itemView.context
            itemView.findViewById<Button>(R.id.btn_leave_class)?.setOnClickListener {
                if (studyClass != null && studyClass!!._id != null) {

                    AlertDialog.Builder(context!!)
                        .setTitle("Are you sure you want to leave ${studyClass?.name ?: "this class"}?")
                        .setPositiveButton("Yes") { _, _ ->

                            ClassServices.leaveClass(studyClass!!._id!!, onResult = {
                                if (index != null)
                                    adapter.removeItemAt(index!!)
                            }, onError = {
                                Toast.makeText(context, "Couldn't leave class", Toast.LENGTH_LONG).show()
                            })

                        }
                        .setNegativeButton("No") { _, _ -> }
                        .show()

                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinedClassesListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.joined_class_list_item, parent, false)
        return JoinedClassesListItemViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: JoinedClassesListItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.index = position
        holder.studyClass = currentItem
        holder.nameTxt.text = currentItem.name
    }

    fun removeItemAt(index: Int) {
        itemList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun getItemCount() = itemList.size
}