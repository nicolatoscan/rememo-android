package com.rememo.models

data class Word(
        val _id: String? = null,
        val index: Int = 1,
        val original: String = "",
        val translation: String = "",
) {}
