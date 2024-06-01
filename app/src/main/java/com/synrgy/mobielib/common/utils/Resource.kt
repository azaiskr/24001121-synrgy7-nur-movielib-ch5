package com.synrgy.mobielib.common.utils

sealed class Resource<out R> private constructor() {
    data object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val exception: String?): Resource<Nothing>()
}