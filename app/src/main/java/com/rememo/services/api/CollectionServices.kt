package com.rememo.services.api

import android.util.Log
import com.rememo.models.Collection

object CollectionServices {

    fun getCollections(onResult: (List<Collection>) -> Unit, onError: (String) -> Unit) {
        APIWrapper.get<List<Collection>>("/collections", onResult = onResult, onError = onError)
    }
}