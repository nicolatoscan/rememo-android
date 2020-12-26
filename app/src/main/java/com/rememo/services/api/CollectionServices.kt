package com.rememo.services.api

import android.util.Log
import com.rememo.models.Collection
import com.rememo.models.CreatedId
import com.rememo.models.Word

object CollectionServices {

    fun getCollections(onResult: (List<Collection>) -> Unit, onError: (String) -> Unit) {
        APIWrapper.get<List<Collection>>("/collections", onResult = onResult, onError = onError)
    }

    fun getCollectionsById(id: String, onResult: (Collection) -> Unit, onError: (String) -> Unit) {
        APIWrapper.get<Collection>("/collections/$id", onResult = onResult, onError = onError)
    }

    fun deleteCollection(id: String, onResult: () -> Unit, onError: (String) -> Unit) {
        APIWrapper.delete("/collections/$id", onResult = onResult, onError = onError)
    }

    fun createCollection(collection: Collection, onResult: (CreatedId) -> Unit, onError: (String) -> Unit) {
        APIWrapper.post<CreatedId, Collection>("/collections", collection, onResult = onResult, onError = onError)
    }

    fun insertWord(collectionId: String, word: Word, onResult: (CreatedId) -> Unit, onError: (String) -> Unit) {
        APIWrapper.post<CreatedId, Word>("/collections/$collectionId/words", word, onResult = onResult, onError = onError)
    }

    fun deleteWord(collectionId: String, wordId: String, onResult: () -> Unit, onError: (String) -> Unit) {
        APIWrapper.delete("/collections/$collectionId/words/$wordId", onResult = onResult, onError = onError)
    }
}