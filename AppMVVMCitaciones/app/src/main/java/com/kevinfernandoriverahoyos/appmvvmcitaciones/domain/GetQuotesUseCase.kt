package com.kevinfernandoriverahoyos.appmvvmcitaciones.domain

import com.kevinfernandoriverahoyos.appmvvmcitaciones.data.network.repository.QuoteRepository

class GetQuotesUseCase {
    private val repository = QuoteRepository()

    suspend operator fun invoke() = repository.getAllQuotes()
}