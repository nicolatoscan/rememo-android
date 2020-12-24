package com.rememo.ui.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is study Fragment"
    }
    val text: LiveData<String> = _text
}