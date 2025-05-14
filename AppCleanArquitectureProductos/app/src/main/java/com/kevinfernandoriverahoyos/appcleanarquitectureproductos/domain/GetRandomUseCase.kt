package com.kevinfernandoriverahoyos.appcleanarquitectureproductos.domain

import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.model.QuoteModel
import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.data.model.QuoteProvider

class GetRandomQuoteUseCase {

    operator fun invoke():QuoteModel?{
        val quotes = QuoteProvider.quotes
        if(!quotes.isNullOrEmpty()){
            val randomNumber = (quotes.indices).random()
            return quotes[randomNumber]
        }
        return null
    }
}