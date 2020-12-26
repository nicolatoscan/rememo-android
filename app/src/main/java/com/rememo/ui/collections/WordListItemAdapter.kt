package com.rememo.ui.collections

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.rememo.R
import com.rememo.models.Word

class WordListItemAdapter(private val itemList: List<Word>, private val fragmentManager: FragmentManager): RecyclerView.Adapter<WordListItemAdapter.WordListItemViewHolder>() {

    class WordListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var word: Word? = null
        val nameTxt: TextView = itemView.findViewById(R.id.collection_name_txt)
        val descriptionTxt: TextView = itemView.findViewById(R.id.collection_description_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_list_item, parent, false)
        return WordListItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordListItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.word = currentItem
        holder.nameTxt.text = currentItem.original
        holder.descriptionTxt.text = currentItem.translation
    }

    override fun getItemCount() = itemList.size
}