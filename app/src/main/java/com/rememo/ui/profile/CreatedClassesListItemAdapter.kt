package com.rememo.ui.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rememo.R
import com.rememo.models.StudyClass

class CreatedClassesListItemAdapter(private val itemList: List<StudyClass>): RecyclerView.Adapter<CreatedClassesListItemAdapter.CreatedClassesListItemViewHolder>() {

    class CreatedClassesListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var studyClass: StudyClass? = null
        val nameTxt: TextView = itemView.findViewById(R.id.txt_title)

        init {
            itemView.findViewById<Button>(R.id.btn_share_class)?.setOnClickListener {
                if (studyClass?._id != null) {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "https://rememo.nicolatoscan.dev/join/${studyClass?._id}")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    itemView.context.startActivity(shareIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatedClassesListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.created_class_list_item, parent, false)
        return CreatedClassesListItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CreatedClassesListItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.studyClass = currentItem
        holder.nameTxt.text = currentItem.name
    }

    override fun getItemCount() = itemList.size
}