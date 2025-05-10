package com.kevinfernandoriverahoyos.appmvvmcitaciones.data.network

import com.kevinfernandoriverahoyos.appmvvmcitaciones.core.RetrofitHelper
import com.kevinfernandoriverahoyos.appmvvmcitaciones.data.model.QuoteModel

class QuoteService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getQuotes(): List<QuoteModel> {
        val response = retrofit.create(QuoteApiClient::class.java).getAllQuotes()
        return response.body() ?: emptyList()
    }
}