package com.rememo.services.api

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson

object APIWrapper {
    suspend inline fun <reified T> apiGet(url: String, onResult: (T) -> Unit, onError: (String) -> Unit) {

         val (request, response, result) = Fuel.get("https://httpbin.org/ip").awaitStringResponseResult()

        result.fold(
            { data ->
                val res = Gson().fromJson(data, T::class.java)
                onResult(res)
            },
            { error -> onError(error.message ?: "Unknown Error") }
        )
    }
}