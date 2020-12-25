package com.rememo.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rememo.R
import com.rememo.models.Collection
import com.rememo.services.api.CollectionServices

class CollectionsFragment : Fragment() {

    private lateinit var collectionsViewModel: CollectionsViewModel

    val collectionList = ArrayList<Collection>()
    val collectionListAdapter = CollectionListItemAdapter(collectionList)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        collectionsViewModel = ViewModelProvider(this).get(CollectionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_collections, container, false)
        // val textView: TextView = root.findViewById(R.id.text_home)
        // collectionsViewModel.text.observe(viewLifecycleOwner, Observer {
            // textView.text = it
        // })

        val recyclerView = root.findViewById<RecyclerView>(R.id.collections_list)
        recyclerView.adapter = collectionListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val loadingSpinner = root.findViewById<ProgressBar>(R.id.loading_collections)
        loadingSpinner.isVisible = true


        CollectionServices.getCollections(onResult = { collections ->
            collectionList.addAll(collections)
            collectionListAdapter.notifyDataSetChanged()
            loadingSpinner.isVisible = false
        }, onError = { error ->

        })

        return root
    }
}