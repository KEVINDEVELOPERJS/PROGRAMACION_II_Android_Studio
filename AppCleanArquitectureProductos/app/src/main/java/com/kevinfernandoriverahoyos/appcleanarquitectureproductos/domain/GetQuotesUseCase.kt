package com.kevinfernandoriverahoyos.appcleanarquitectureproductos.domain

import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.model.QuoteModel
import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.repository.QuoteRepository

class GetQuotesUseCase {

    private val repository = QuoteRepository()

    suspend operator fun invoke():List<QuoteModel>? = repository.getAllQuotes()

}