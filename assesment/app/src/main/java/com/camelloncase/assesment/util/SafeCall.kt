package com.camelloncase.assesment.util

import android.util.Log

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An unknown error was occurred!")
    }
}

inline fun <T> responseSafeCall(action: () -> Response<T>): Response<T> {
    return try {
        action()
    } catch (e: Exception) {
        Response.Failure(e.message ?: "An unknown error was occurred!")
    }
}


