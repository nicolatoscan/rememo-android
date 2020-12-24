package com.rememo.services.api

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.json.FuelJson
import com.github.kittinunf.fuel.json.responseJson
import com.google.gson.Gson
import com.rememo.models.Pippo
import kotlinx.serialization.decodeFromString

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