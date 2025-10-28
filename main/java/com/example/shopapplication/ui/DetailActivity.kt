package com.example.shopapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.shopapplication.R
import com.example.shopapplication.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple activity to show the details of a product.
 * It receives the data via intent extras.
 * */
@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    private var binding: ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val id = intent.getIntExtra("id", -1)
        val name = intent.getStringExtra("name")
        val clients = intent.getIntExtra("clients", 0)

        binding?.run {
            title.text = name
            subtitle.text = getString(R.string.detail_subtitle, id, clients)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}