package com.rememo.models

data class StudyClass(
    val _id: String? = null,
    val name: String = "",
    val collections: ArrayList<String> = ArrayList(),
    val mine: Boolean? = null
)
