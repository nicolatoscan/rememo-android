package com.rememo.ui.collections

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

class CollectionListItemAdapter(private val itemList: List<Collection>, private val fragmentManager: FragmentManager): RecyclerView.Adapter<CollectionListItemAdapter.CollectionListItemViewHolder>() {

    class CollectionListItemViewHolder(itemView: View, private val fragmentManager: FragmentManager): RecyclerView.ViewHolder(itemView) {
        var collection: Collection? = null
        val nameTxt: TextView = itemView.findViewById(R.id.collection_name_txt)
        val descriptionTxt: TextView = itemView.findViewById(R.id.collection_description_txt)

        init {
            itemView.setOnClickListener { onClick(it) }
        }

        private fun onClick(v: View) {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, CollectionDetailsFragment.newInstance(collection?._id ?: "", collection?.name ?: ""))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_list_item, parent, false)
        return CollectionListItemViewHolder(itemView, fragmentManager)
    }

    override fun onBindViewHolder(holder: CollectionListItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.collection = currentItem
        holder.nameTxt.text = currentItem.name
        holder.descriptionTxt.text = currentItem.description
    }

    override fun getItemCount() = itemList.size
}