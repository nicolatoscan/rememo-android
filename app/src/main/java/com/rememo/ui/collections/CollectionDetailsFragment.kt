package com.rememo.ui.collections

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.rememo.MainActivity
import com.rememo.R
import com.rememo.services.api.CollectionServices

private const val ARG_COLLECTION_ID = "collectionId"
private const val ARG_COLLECTION_NAME = "collectionName"

class CollectionDetailsFragment : Fragment() {
    private var collectionId: String? = null
    private var collectionName: String? = null

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
        val v = inflater.inflate(R.layout.fragment_collection_details, container, false)
        (activity as MainActivity).setCustomActionBarTitle(collectionName ?: "Collection")
        v.findViewById<Button>(R.id.button).setOnClickListener {
            //TODO: make back button work
            (activity as MainActivity).removeCustomActionBarTitle()
            this.parentFragmentManager.popBackStack()
        }

        val txtDescription = v.findViewById<TextView>(R.id.txt_description)
        val loadingSpinner = v.findViewById<ProgressBar>(R.id.loading_spinner)
        loadingSpinner.isVisible = true

        if (collectionId != null) {
            CollectionServices.getCollectionsById(collectionId!!, onResult = { collection ->
                txtDescription.text = collection.description
                loadingSpinner.isVisible = false
            }, onError = {
                txtDescription.text = "Couldn't find this collections, Please try again later"
                loadingSpinner.isVisible = false
            })

        }


        return v;
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