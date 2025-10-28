package com.example.shopapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shopapplication.databinding.ActivityMainBinding
import com.example.shopapplication.ui.TabsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val adapter = TabsPagerAdapter(this)

        /** Setup the ViewPager2 with the adapter
         * */
        binding?.run {
            viewPager.offscreenPageLimit = 3
            viewPager.adapter = adapter
        }

        /***
         * Attach the TabLayout with the ViewPager2
         * */
        binding?.let {
            TabLayoutMediator(it.tabs, it.viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Tab1"
                    1 -> "Tab2"
                    else -> "Tab3"
                }
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}