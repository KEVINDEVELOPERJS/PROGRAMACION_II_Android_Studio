package com.kevinfernandoriverahoyos.appmvvmcitaciones.data.network.repository

import com.kevinfernandoriverahoyos.appmvvmcitaciones.data.model.QuoteModel
import com.kevinfernandoriverahoyos.appmvvmcitaciones.data.model.QuoteProvider
import com.kevinfernandoriverahoyos.appmvvmcitaciones.data.network.QuoteService

class QuoteRepository {
    private val api = QuoteService()

    suspend fun getAllQuotes(): List<QuoteModel> {
        val response = api.getQuotes()
        QuoteProvider.quotes = response
        return response
    }
}