package com.rememo.services

object AuthenticationHelper {
    private var token: String? = null

    fun isLogged(): Boolean {
        return getToken() != null
    }

    fun getToken(): String? {
        if (token != null)
            return token

        var token = AppPreferencesHelper.authToken
        if (token?.isNotEmpty() == true) {
            AuthenticationHelper.token = token
            return token
        }
        return null
    }

    fun logIn(token: String) {
        if (token.isNotEmpty()) {
            AppPreferencesHelper.authToken = token
            AuthenticationHelper.token = token
        }
    }

    fun logOut() {
        token = null
        AppPreferencesHelper.authToken = ""
    }
}