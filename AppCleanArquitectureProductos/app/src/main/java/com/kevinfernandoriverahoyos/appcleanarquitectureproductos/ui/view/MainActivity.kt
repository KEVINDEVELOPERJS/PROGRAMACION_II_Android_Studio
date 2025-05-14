package com.kevinfernandoriverahoyos.appcleanarquitectureproductos.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.databinding.ActivityMainBinding
import com.kevinfernandoriverahoyos.appcleanarquitectureproductos.ui.viewmodel.QuoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quoteViewModel: QuoteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        quoteViewModel.onCreate()

        quoteViewModel.quoteModel.observe(this, Observer {
            binding.tvQuote.text = it.quote
            binding.tvAuthor.text = it.author
        })
        quoteViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })


        binding.viewContainer.setOnClickListener { quoteViewModel.randomQuote() }

    }

}

