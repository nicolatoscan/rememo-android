package com.rememo.ui.collections

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rememo.MainActivity
import com.rememo.R
import com.rememo.services.api.CollectionServices

class ImportActivity : AppCompatActivity() {
    private class ImportWordsListAdapter(private val itemList: List<String>): RecyclerView.Adapter<ImportWordsListAdapter.ImportWordsListViewHolder>() {
        class ImportWordsListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val txt: TextView = itemView.findViewById(R.id.txt_line)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImportWordsListViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.one_line_list_item,
                parent,
                false
            )
            return ImportWordsListViewHolder(itemView)
        }
        override fun onBindViewHolder(holder: ImportWordsListViewHolder, position: Int) {
            holder.txt.text = itemList[position]
        }
        override fun getItemCount() = itemList.size
    }

    private var collectionId: String? = null
    private var loadingSpinner: ProgressBar? = null
    private var btnImport: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import)

        val data: Uri? = intent?.data
        collectionId = data?.toString()?.split("/")?.last()

        val txtName = findViewById<TextView>(R.id.txt_import_collection_name)
        val txtDescription = findViewById<TextView>(R.id.txt_import_collection_description)
        btnImport = findViewById<Button>(R.id.btn_import)
        loadingSpinner = findViewById<ProgressBar>(R.id.loading_import_collection)

        val wordList = ArrayList<String>()
        val wordListAdapter = ImportWordsListAdapter(wordList)
        val wordRecyclerView = findViewById<RecyclerView>(R.id.import_word_list)
        wordRecyclerView?.adapter = wordListAdapter
        wordRecyclerView?.layoutManager = LinearLayoutManager(this)
        wordRecyclerView?.setHasFixedSize(true)


        if (collectionId != null) {

            CollectionServices.getCollectionsById(collectionId!!, onResult = { collection ->
                txtName.text = collection.name
                txtDescription.text = collection.description
                wordList.addAll(collection.words.map { w -> "${w.original}: ${w.translation}" })
                btnImport?.isEnabled = true
                loadingSpinner?.isVisible = false
            }, onError = {
                txtName.text = "Couldn't find any collection to import"
            })

        } else {
            txtName.text = "Couldn't find any collection to import"
        }

        btnImport?.setOnClickListener {
            importCollection()
        }
    }

    private fun importCollection() {
        if (collectionId == null)
            return

        btnImport?.isEnabled = false
        loadingSpinner?.isVisible = true

        CollectionServices.importCollection(collectionId!!, onResult = {
            Toast.makeText(this, "Collection imported!", Toast.LENGTH_LONG).show()

            val parentIntent = Intent(this, MainActivity::class.java)
            startActivity(parentIntent)
            finish()

        }, onError = {
            Toast.makeText(this, "Couldn't import the collection", Toast.LENGTH_LONG).show()
            btnImport?.isEnabled = true
            loadingSpinner?.isVisible = false
        })

    }
}
