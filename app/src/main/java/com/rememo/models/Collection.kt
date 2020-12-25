package com.rememo.models

data class Collection(
    val _id: String? = null,
    val name: String = "",
    val description: String = "",
    val languageFrom: String? = null,
    val languageTo: String? = null,
    val words: List<Word> = emptyList(),
    val index: Int = 1
)
