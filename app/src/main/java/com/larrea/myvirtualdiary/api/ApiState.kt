package com.larrea.myvirtualdiary.api

sealed class ApiState<out T> {

    object Loading : ApiState<Nothing>()

    data class Success<T>(
        val data: T
    ) : ApiState<T>()

    data class Error(
        val mensaje: String
    ) : ApiState<Nothing>()
}