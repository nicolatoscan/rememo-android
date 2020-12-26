package com.rememo.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rememo.R
import com.rememo.models.StudyClass

class JoinedClassesListItemAdapter(private val itemList: List<StudyClass>): RecyclerView.Adapter<JoinedClassesListItemAdapter.JoinedClassesListItemViewHolder>() {

    class JoinedClassesListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var studyClass: StudyClass? = null
        val nameTxt: TextView = itemView.findViewById(R.id.txt_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinedClassesListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.joined_class_list_item, parent, false)
        return JoinedClassesListItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JoinedClassesListItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.studyClass = currentItem
        holder.nameTxt.text = currentItem.name
    }

    override fun getItemCount() = itemList.size
}