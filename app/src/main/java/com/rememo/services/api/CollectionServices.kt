package com.rememo.services.api

import android.util.Log
import com.rememo.models.Collection
import com.rememo.models.CreatedId

object CollectionServices {

    fun getCollections(onResult: (List<Collection>) -> Unit, onError: (String) -> Unit) {
        APIWrapper.get<List<Collection>>("/collections", onResult = onResult, onError = onError)
    }

    fun createCollection(collection: Collection, onResult: (CreatedId) -> Unit, onError: (String) -> Unit) {
        APIWrapper.post<CreatedId, Collection>("/collections", collection, onResult = onResult, onError = onError)
    }
}