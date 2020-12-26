package com.rememo.ui.collections

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.rememo.R
import com.rememo.services.api.CollectionServices

class WordDetailsActivity : AppCompatActivity() {
    private var collectionId: String? = null
    private var wordId: String? = null
    private var wordName: String? = null

    private var loadingSpinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_details)

        collectionId = intent.getStringExtra("00")
        wordId = intent.getStringExtra("AA")
        wordName = intent.getStringExtra("BB")
        supportActionBar?.title = wordName

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.collection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (wordId == null)
            return false

        AlertDialog.Builder(this)
            .setTitle("Are you sure you want to delete this word?")
            .setPositiveButton("Yes") { _, _ ->
                loadingSpinner?.isVisible = true
                CollectionServices.deleteWord(collectionId!!, wordId!!, onResult = {
                    finish()
                }, onError = {
                    Toast.makeText(this, "Couldn't delete word", Toast.LENGTH_LONG).show()
                })
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
        return true
    }
}