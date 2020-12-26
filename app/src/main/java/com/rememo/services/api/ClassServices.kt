package com.rememo.services.api

import android.util.Log
import com.rememo.models.Collection
import com.rememo.models.CreatedId
import com.rememo.models.StudyClass
import com.rememo.models.Word

object ClassServices {

    fun getClasses(onResult: (List<StudyClass>) -> Unit, onError: (String) -> Unit) {
        APIWrapper.get<List<StudyClass>>("/class", onResult = onResult, onError = onError)
    }
}