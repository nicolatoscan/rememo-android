package com.rememo.services.api

import android.util.Log
import com.rememo.models.*
import com.rememo.models.Collection

object ClassServices {

    fun getClasses(onResult: (List<StudyClass>) -> Unit, onError: (String) -> Unit) {
        APIWrapper.get<List<StudyClass>>("/class", onResult = onResult, onError = onError)
    }

    fun createClass(studyClass: StudyClassMin, onResult: (CreatedId) -> Unit, onError: (String) -> Unit) {
        APIWrapper.post<CreatedId, StudyClassMin>("/class", studyClass, onResult = onResult, onError = onError)
    }

    fun leaveClass(classId: String, onResult: (EmptyResult) -> Unit, onError: (String) -> Unit) {
        APIWrapper.put<EmptyResult, EmptyResult>("/class/$classId/leave", EmptyResult(), onResult = onResult, onError = onError)
    }}