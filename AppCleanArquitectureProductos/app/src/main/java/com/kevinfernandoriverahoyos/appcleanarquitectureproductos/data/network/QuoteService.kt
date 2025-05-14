package com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.network

import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.core.RetrofitHelper
import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.model.QuoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getQuotes(): List<QuoteModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(QuoteApiClient::class.java).getAllQuotes()
            response.body() ?: emptyList()
        }
    }

}