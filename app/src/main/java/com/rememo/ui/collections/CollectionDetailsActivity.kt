package com.rememo.ui.collections

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rememo.MainActivity
import com.rememo.R
import com.rememo.models.Word
import com.rememo.services.api.CollectionServices

class CollectionDetailsActivity : AppCompatActivity() {
    private var collectionId: String? = null
    private var collectionName: String? = null

    private val wordList = ArrayList<Word>()
    private var wordListAdapter: WordListItemAdapter? = null
    private var wordRecyclerView: RecyclerView? = null

    private var txtEditOriginal: EditText? = null
    private var txtEditTranslation: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_details)

        collectionId = intent.getStringExtra("AA")
        supportActionBar?.title = intent.getStringExtra("BB")

        val txtDescription = findViewById<TextView>(R.id.txt_description)
        val loadingSpinner = findViewById<ProgressBar>(R.id.loading_spinner)
        loadingSpinner.isVisible = true

        wordListAdapter = WordListItemAdapter(wordList, this.supportFragmentManager)
        wordRecyclerView = findViewById<RecyclerView>(R.id.word_list)
        wordRecyclerView?.adapter = wordListAdapter
        wordRecyclerView?.layoutManager = LinearLayoutManager(this)
        wordRecyclerView?.setHasFixedSize(true)

        if (collectionId != null) {
            CollectionServices.getCollectionsById(collectionId!!, onResult = { collection ->
                txtDescription.text = collection.description

                wordList.clear()
                wordList.addAll(collection.words)
                wordListAdapter?.notifyDataSetChanged()

                loadingSpinner.isVisible = false
            }, onError = {
                txtDescription.text = "Couldn't find this collections, Please try again later"
                loadingSpinner.isVisible = false
            })
        }

        txtEditOriginal = findViewById<EditText>(R.id.txt_edit_original)
        txtEditTranslation = findViewById<EditText>(R.id.txt_edit_translation)

        findViewById<ImageButton>(R.id.btn_insert_word).setOnClickListener { onClickInsertWord() }
    }

    private fun onClickInsertWord() {
        if (collectionId == null)
            return

        val original = txtEditOriginal?.text.toString()
        val translation = txtEditTranslation?.text.toString()

        if (original == null || original.isEmpty() || original.isBlank()) {
            Toast.makeText(this, "Insert a original text", Toast.LENGTH_LONG).show()
            return
        }
        if (translation == null || translation.isEmpty() || translation.isBlank()) {
            Toast.makeText(this, "Insert a translation text", Toast.LENGTH_LONG).show()
            return
        }

        val word = Word(original = original, translation = translation)
        val insertedIndex = wordList.size
        wordList.add(word)
        wordListAdapter?.notifyItemInserted(wordList.size - 1)
        wordRecyclerView?.scrollToPosition(wordList.size - 1)

        txtEditOriginal?.text?.clear()
        txtEditTranslation?.text?.clear()

        CollectionServices.insertWord(collectionId!!, word, onResult = { }, onError = { error ->
            Toast.makeText(this, "Couldn't insert  word", Toast.LENGTH_LONG).show()
            wordList.removeAt(insertedIndex)
            wordListAdapter?.notifyItemRemoved(insertedIndex)
        })

    }

}