package com.kevinfernandoriverahoyos.appmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kevinfernandoriverahoyos.appmvvm.model.QuoteModel
import com.kevinfernandoriverahoyos.appmvvm.model.QuoteProvider

class QuoteViewModel : ViewModel() {


    val quoteModel = MutableLiveData<QuoteModel>()

    fun randomQuote() {
        val currentQuote = QuoteProvider.random()
        quoteModel.postValue(currentQuote)
    }
}