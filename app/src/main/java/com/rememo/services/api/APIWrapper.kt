package com.rememo.services.api

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rememo.services.AuthenticationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object APIWrapper {

    final val baseUrl = "https://rememo-api.herokuapp.com/api/v2"

    suspend inline fun <reified T> handleResult(
        result: com.github.kittinunf.result.Result<String, FuelError>,
        httpCode: Int,
        crossinline onResult: (T) -> Unit,
        crossinline onError: (String) -> Unit
    ) {
        result.fold(
            { data ->
                if (httpCode >= 300) {
                    withContext(Dispatchers.Main) { onError("Code $httpCode: $data") }
                } else {
                    val itemType = object : TypeToken<T>() {}.type
                    val res = Gson().fromJson<T>(data, itemType)
                    withContext(Dispatchers.Main) { onResult(res) }
                }

            },
            { error ->
                withContext(Dispatchers.Main) { onError(error.message ?: "Unknown Error") }
            }
        )
    }

    inline fun <reified T> get(
        api: String,
        crossinline onResult: (T) -> Unit,
        crossinline onError: (String) -> Unit
    ) {
        GlobalScope.launch {
            val token = AuthenticationHelper.getToken()
            val (request, response, result) = Fuel.get("$baseUrl$api")
                .appendHeader("Authorization", AuthenticationHelper.getToken() ?: "")
                .awaitStringResponseResult()
            handleResult<T>(result, response.statusCode, onResult, onError)
        }
    }

    inline fun <reified T1, T2> post(
        api: String,
        body: T2,
        crossinline onResult: (T1) -> Unit,
        crossinline onError: (String) -> Unit
    ) {
        GlobalScope.launch {
            val (request, response, result) = Fuel.post("$baseUrl$api")
                .body(Gson().toJson(body))
                .appendHeader("Content-Type", "application/json")
                .appendHeader("Authorization", AuthenticationHelper.getToken() ?: "")
                .awaitStringResponseResult()
            handleResult<T1>(result, response.statusCode, onResult, onError)
        }
    }

    inline fun <reified T1, T2> put(
        api: String,
        body: T2,
        crossinline onResult: (T1) -> Unit,
        crossinline onError: (String) -> Unit
    ) {
        GlobalScope.launch {
            val (request, response, result) = Fuel.put("$baseUrl$api")
                .body(Gson().toJson(body))
                .appendHeader("Content-Type", "application/json")
                .appendHeader("Authorization", AuthenticationHelper.getToken() ?: "")
                .awaitStringResponseResult()
            handleResult<T1>(result, response.statusCode, onResult, onError)
        }
    }

    inline fun delete(
        api: String,
        crossinline onResult: () -> Unit,
        crossinline onError: (String) -> Unit
    ) {
        GlobalScope.launch {
            val (request, response, result) = Fuel.delete("$baseUrl$api")
                .appendHeader("Authorization", AuthenticationHelper.getToken() ?: "")
                .awaitStringResponseResult()

            result.fold(
                { data ->
                    if (response.statusCode >= 300) {
                        withContext(Dispatchers.Main) { onError("Code $response.statusCode: $data") }
                    } else {
                        withContext(Dispatchers.Main) { onResult() }
                    }
                },
                { error ->
                    withContext(Dispatchers.Main) { onError(error.message ?: "Unknown Error") }
                }
            )
        }
    }
}
