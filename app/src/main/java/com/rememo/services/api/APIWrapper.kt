package com.rememo.services.api

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rememo.services.AuthenticationHelper

object APIWrapper {

    final val baseUrl = "https://rememo-api.herokuapp.com/api/v2"

    suspend inline fun <reified T> handleResult(result: com.github.kittinunf.result.Result<String, FuelError>, httpCode: Int, onResult: (T) -> Unit, onError: (String) -> Unit) {
        result.fold(
                { data ->
                    if (httpCode >= 300) {
                        onError("Code $httpCode: $data")
                        return
                    }
                    val itemType = object : TypeToken<T>() {}.type
                    val res = Gson().fromJson<T>(data, itemType)
                    onResult(res)
                },
                { error ->
                    onError(error.message ?: "Unknown Error")
                }
        )
    }

    suspend inline fun <reified T> get(api: String, onResult: (T) -> Unit, onError: (String) -> Unit) {
        val token = AuthenticationHelper.getToken()
        val (request, response, result) = Fuel.get("$baseUrl$api")
                .appendHeader("Authorization", AuthenticationHelper.getToken() ?: "")
                .awaitStringResponseResult()
        handleResult<T>(result, response.statusCode, onResult, onError)
    }

    suspend inline fun <reified T1, T2> post(api: String, body: T2, onResult: (T1) -> Unit, onError: (String) -> Unit) {

        val (request, response, result) = Fuel.post("$baseUrl$api")
                .body(Gson().toJson(body))
                .appendHeader("Authorization", AuthenticationHelper.getToken() ?: "")
                .awaitStringResponseResult()
        handleResult<T1>(result, response.statusCode, onResult, onError)
    }

    suspend inline fun <reified T1, T2> put(api: String, body: T2, onResult: (T1) -> Unit, onError: (String) -> Unit) {
        val (request, response, result) = Fuel.put("$baseUrl$api")
                .body(Gson().toJson(body))
                .appendHeader("Authorization", AuthenticationHelper.getToken() ?: "")
                .awaitStringResponseResult()
        handleResult<T1>(result, response.statusCode, onResult, onError)
    }

    suspend inline fun <reified T> delete(api: String, onResult: (T) -> Unit, onError: (String) -> Unit) {
        val (request, response, result) = Fuel.delete("$baseUrl$api")
                .appendHeader("Authorization", AuthenticationHelper.getToken() ?: "")
                .awaitStringResponseResult()
        handleResult<T>(result, response.statusCode, onResult, onError)
    }
}