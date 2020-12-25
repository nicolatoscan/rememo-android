package com.rememo.ui.collections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rememo.R
import com.rememo.models.Collection

class CollectionListItemAdapter(private val itemList: List<Collection>): RecyclerView.Adapter<CollectionListItemAdapter.CollectionListItemViewHolder>() {

    class CollectionListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTxt: TextView = itemView.findViewById(R.id.collection_name_txt)
        val descriptionTxt: TextView = itemView.findViewById(R.id.collection_description_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_list_item, parent, false)
        return CollectionListItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CollectionListItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.nameTxt.text = currentItem.name
        holder.descriptionTxt.text = currentItem.description
    }

    override fun getItemCount() = itemList.size
}