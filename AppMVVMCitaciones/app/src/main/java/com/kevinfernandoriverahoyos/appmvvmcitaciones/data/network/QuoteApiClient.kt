package com.kevinfernandoriverahoyos.appmvvmcitaciones.data.network

import com.kevinfernandoriverahoyos.appmvvmcitaciones.data.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApiClient {
    @GET("/.json")
    suspend fun getAllQuotes(): Response<List<QuoteModel>>
}