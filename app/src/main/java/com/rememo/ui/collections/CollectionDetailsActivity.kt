package com.rememo.ui.collections

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rememo.MainActivity
import com.rememo.R
import com.rememo.models.Collection
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
    private var loadingSpinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_details)

        collectionId = intent.getStringExtra("AA")
        collectionName = intent.getStringExtra("BB")
        supportActionBar?.title = collectionName

        val txtDescription = findViewById<TextView>(R.id.txt_description)
        loadingSpinner = findViewById<ProgressBar>(R.id.loading_spinner)
        loadingSpinner?.isVisible = true

        wordListAdapter = WordListItemAdapter(wordList, collectionId ?: "")
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

                loadingSpinner?.isVisible = false
            }, onError = {
                txtDescription.text = "Couldn't find this collections, Please try again later"
                loadingSpinner?.isVisible = false
            })
        }

        txtEditOriginal = findViewById<EditText>(R.id.txt_edit_original)
        txtEditTranslation = findViewById<EditText>(R.id.txt_edit_translation)

        findViewById<ImageButton>(R.id.btn_insert_word).setOnClickListener { onClickInsertWord() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.collection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (collectionId == null)
            return false

        when (item.itemId) {
            R.id.menu_item_share -> {
                shareCollection()
                return true
            }
            R.id.menu_item_delete -> {
                deleteCollection()
                return true
            }
        }

        return true
    }

    private fun shareCollection() {
        CollectionServices.shareCollection(collectionId!!, onResult = { url ->
           val sendIntent = Intent().apply {
               action = Intent.ACTION_SEND
               putExtra(Intent.EXTRA_TEXT, "https://rememo.nicolatoscan.dev/#/import/${collectionId}")
               type = "text/plain"
           }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }, onError = {
            Toast.makeText(this, "Couldn't share this collection", Toast.LENGTH_LONG).show()
        })
    }

    private fun deleteCollection() {
        AlertDialog.Builder(this)
            .setTitle("Are you sure you want to delete $collectionName?")
            .setPositiveButton("Yes") { _, _ ->
                loadingSpinner?.isVisible = true
                CollectionServices.deleteCollection(collectionId!!, onResult = {
                    finish()
                }, onError = {
                    Toast.makeText(this, "Couldn't delete collection", Toast.LENGTH_LONG).show()
                })
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
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

        txtEditOriginal?.text?.clear()
        txtEditTranslation?.text?.clear()

        CollectionServices.insertWord(collectionId!!, Word(original = original, translation = translation), onResult = { id ->

            val word = Word(_id = id._id, original = original, translation = translation)
            wordList.add(word)
            wordListAdapter?.notifyItemInserted(wordList.size - 1)
            wordRecyclerView?.scrollToPosition(wordList.size - 1)

        }, onError = { error ->
            Toast.makeText(this, "Couldn't insert  word", Toast.LENGTH_LONG).show()
        })

    }

}