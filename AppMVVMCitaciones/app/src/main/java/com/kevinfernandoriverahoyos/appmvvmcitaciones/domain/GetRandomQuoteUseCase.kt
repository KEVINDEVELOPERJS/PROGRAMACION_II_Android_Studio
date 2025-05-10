package com.kevinfernandoriverahoyos.appmvvmcitaciones.domain

import com.kevinfernandoriverahoyos.appmvvmcitaciones.data.model.QuoteModel
import com.kevinfernandoriverahoyos.appmvvmcitaciones.data.model.QuoteProvider

class GetRandomQuoteUseCase {


    operator fun invoke():QuoteModel?{
        val quotes = QuoteProvider.quotes
        if(quotes.isNullOrEmpty()){
            val randomNumber = (quotes.indices).random()
            return quotes[randomNumber]
        }
        return null
    }
}