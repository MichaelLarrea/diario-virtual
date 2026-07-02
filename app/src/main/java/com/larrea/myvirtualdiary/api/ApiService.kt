package com.larrea.myvirtualdiary.api

import retrofit2.http.GET

interface ApiService {

    @GET("quotes/random")
    suspend fun obtenerFraseAleatoria(): FraseResponse
}