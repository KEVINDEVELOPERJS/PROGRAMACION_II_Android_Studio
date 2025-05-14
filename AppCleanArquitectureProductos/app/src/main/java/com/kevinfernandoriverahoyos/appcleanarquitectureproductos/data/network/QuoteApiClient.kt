package com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.network

import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApiClient {
    @GET("/.json")
    suspend fun getAllQuotes(): Response<List<QuoteModel>>
}