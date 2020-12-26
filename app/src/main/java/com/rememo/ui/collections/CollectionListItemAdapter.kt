package com.rememo.ui.collections

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.rememo.R
import com.rememo.models.Collection

class CollectionListItemAdapter(private val itemList: List<Collection>): RecyclerView.Adapter<CollectionListItemAdapter.CollectionListItemViewHolder>() {

    class CollectionListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var context: Context? = null
        var collection: Collection? = null
        val nameTxt: TextView = itemView.findViewById(R.id.collection_name_txt)
        val descriptionTxt: TextView = itemView.findViewById(R.id.collection_description_txt)

        init {
            itemView.setOnClickListener { onClick(it) }
            context = itemView.context
        }

        private fun onClick(v: View) {
            val intent = Intent(context, CollectionDetailsActivity::class.java).apply {
                putExtra("AA", collection?._id ?: "")
                putExtra("BB", collection?.name ?: "")
            }
            context?.startActivity(intent)
            return
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_list_item, parent, false)
        return CollectionListItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CollectionListItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.collection = currentItem
        holder.nameTxt.text = currentItem.name
        holder.descriptionTxt.text = currentItem.description
    }

    override fun getItemCount() = itemList.size
}