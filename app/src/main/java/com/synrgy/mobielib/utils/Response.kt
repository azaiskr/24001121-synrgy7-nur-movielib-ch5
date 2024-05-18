package com.synrgy.mobielib.utils

sealed class Response<out R> private constructor() {
    data object Loading: Response<Nothing>()
    data class Success<out T>(val data: T): Response<T>()
    data class Error(val exception: String?): Response<Nothing>()
}