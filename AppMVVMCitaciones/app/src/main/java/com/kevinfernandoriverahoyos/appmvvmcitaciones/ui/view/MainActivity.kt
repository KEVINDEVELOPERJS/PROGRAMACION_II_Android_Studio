package com.kevinfernandoriverahoyos.appmvvmcitaciones.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kevinfernandoriverahoyos.appmvvmcitaciones.databinding.ActivityMainBinding
import com.kevinfernandoriverahoyos.appmvvmcitaciones.ui.viewmodel.QuoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quoteViewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        quoteViewModel.onCreate()
    }

    private fun setupObservers() {
        quoteViewModel.quoteModel.observe(this, Observer { quote ->
            binding.tvQuote.text = quote.quote
            binding.tvAuthor.text = quote.author
        })

        quoteViewModel.isLoading.observe(this, Observer { isLoading ->
            binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.viewContainer.isClickable = !isLoading

        })
        binding.viewContainer.setOnClickListener { quoteViewModel.randomQuote() }
    }
    override fun onResume() {
        super.onResume()
        binding.viewContainer.setOnClickListener {
                  quoteViewModel.randomQuote()
        }
    }
}



