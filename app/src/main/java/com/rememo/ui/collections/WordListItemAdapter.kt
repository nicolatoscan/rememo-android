package com.rememo.ui.collections

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.rememo.R
import com.rememo.models.Word

class WordListItemAdapter(
    private val itemList: List<Word>,
    private val collectionId: String): RecyclerView.Adapter<WordListItemAdapter.WordListItemViewHolder>() {

    class WordListItemViewHolder(itemView: View, private val collectionId: String): RecyclerView.ViewHolder(itemView) {
        var context: Context? = null
        var word: Word? = null
        val nameTxt: TextView = itemView.findViewById(R.id.collection_name_txt)
        val descriptionTxt: TextView = itemView.findViewById(R.id.collection_description_txt)

        init {
            itemView.setOnClickListener { onClick(it) }
            context = itemView.context
        }

        private fun onClick(v: View) {
            val intent = Intent(context, WordDetailsActivity::class.java).apply {
                putExtra("00", collectionId)
                putExtra("AA", word?._id ?: "")
                putExtra("BB", word?.original ?: "")
            }
            context?.startActivity(intent)
            return
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_list_item, parent, false)
        return WordListItemViewHolder(itemView, collectionId)
    }

    override fun onBindViewHolder(holder: WordListItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.word = currentItem
        holder.nameTxt.text = currentItem.original
        holder.descriptionTxt.text = currentItem.translation
    }

    override fun getItemCount() = itemList.size
}