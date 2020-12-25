package com.rememo.services.api

import android.util.Log
import com.rememo.models.Collection
import com.rememo.models.CreatedId
import com.rememo.models.User

object UsersServices {

    fun getUserInfo(onResult: (User) -> Unit, onError: (String) -> Unit) {
        APIWrapper.get<User>("/users", onResult = onResult, onError = onError)
    }
}