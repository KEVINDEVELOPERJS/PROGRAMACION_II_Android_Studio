package com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.repository

import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.model.QuoteModel
import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.model.QuoteProvider
import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.network.QuoteService

class QuoteRepository {

    private val api = QuoteService()

    suspend fun getAllQuotes():List<QuoteModel>{
        val response = api.getQuotes()
        QuoteProvider.quotes = response
        return response
    }
}