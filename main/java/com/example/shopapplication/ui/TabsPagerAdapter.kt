package com.example.shopapplication.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shopapplication.ui.tab1.Tab1Fragment
import com.example.shopapplication.ui.tab2.Tab2Fragment
import com.example.shopapplication.ui.tab3.Tab3Fragment

class TabsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    /** We have 3 tabs */
    override fun getItemCount(): Int = 3

    /** Create the appropriate Fragment for each tab */
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> Tab1Fragment()
        1 -> Tab2Fragment()
        else -> Tab3Fragment()
    }
}