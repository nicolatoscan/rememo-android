package com.rememo.ui.collections

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rememo.MainActivity
import com.rememo.R
import com.rememo.models.Collection
import com.rememo.models.Word
import com.rememo.services.api.CollectionServices

private const val ARG_COLLECTION_ID = "collectionId"
private const val ARG_COLLECTION_NAME = "collectionName"

class CollectionDetailsFragment : Fragment() {
    private var collectionId: String? = null
    private var collectionName: String? = null

    private val wordList = ArrayList<Word>()
    private var wordListAdapter: WordListItemAdapter? = null
    private var wordRecyclerView: RecyclerView? = null

    private var txtEditOriginal: EditText? = null
    private var txtEditTranslation: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionId = it.getString(ARG_COLLECTION_ID)
            collectionName = it.getString(ARG_COLLECTION_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_collection_details, container, false)
        (activity as MainActivity).setCustomActionBarTitle(collectionName ?: "Collection")
        root.findViewById<Button>(R.id.button).setOnClickListener {
            //TODO: make back button work
            (activity as MainActivity).removeCustomActionBarTitle()
            this.parentFragmentManager.popBackStack()
        }

        val txtDescription = root.findViewById<TextView>(R.id.txt_description)
        val loadingSpinner = root.findViewById<ProgressBar>(R.id.loading_spinner)
        loadingSpinner.isVisible = true

        wordListAdapter = WordListItemAdapter(wordList, this.parentFragmentManager)
        wordRecyclerView = root.findViewById<RecyclerView>(R.id.word_list)
        wordRecyclerView?.adapter = wordListAdapter
        wordRecyclerView?.layoutManager = LinearLayoutManager(context)
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

        txtEditOriginal = root.findViewById<EditText>(R.id.txt_edit_original)
        txtEditTranslation = root.findViewById<EditText>(R.id.txt_edit_translation)

        root.findViewById<ImageButton>(R.id.btn_insert_word).setOnClickListener { onClickInsertWord() }
        return root;
    }

    fun onClickInsertWord() {
        if (collectionId == null)
            return

        val original = txtEditOriginal?.text.toString()
        val translation = txtEditTranslation?.text.toString()

        if (original == null || original.isEmpty() || original.isBlank()) {
            Toast.makeText(context, "Insert a original text", Toast.LENGTH_LONG).show()
            return
        }
        if (translation == null || translation.isEmpty() || translation.isBlank()) {
            Toast.makeText(context, "Insert a translation text", Toast.LENGTH_LONG).show()
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
            Toast.makeText(context, "Couldn't insert  word", Toast.LENGTH_LONG).show()
            wordList.removeAt(insertedIndex)
            wordListAdapter?.notifyItemRemoved(insertedIndex)
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(collectionId: String, collectionName: String) =
            CollectionDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_COLLECTION_ID, collectionId)
                    putString(ARG_COLLECTION_NAME, collectionName)
                }
            }
    }
}