package com.rememo.helpers

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
            this.token = token
            return token
        }
        return null
    }

    fun logIn(token: String) {
        if (token.isNotEmpty()) {
            AppPreferencesHelper.authToken = token
            this.token = token
        }
    }

    fun logOut() {
        this.token = null
        AppPreferencesHelper.authToken = ""
    }
}