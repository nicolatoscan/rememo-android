package com.rememo.models

data class Collection(
    val _id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val languageFrom: String? = null,
    val languageTo: String? = null,
    val words: List<Word> = emptyList()
) {}
