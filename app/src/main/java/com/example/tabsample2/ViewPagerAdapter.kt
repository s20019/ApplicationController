package com.example.tabsample2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tabsample2.fragment.Tab01Fragment
import com.example.tabsample2.fragment.Tab02Fragment

// タブの数
private const val NUM_TUB = 2

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return NUM_TUB
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return Tab01Fragment()
            1 -> return Tab02Fragment()
        }
        return Tab02Fragment()
    }
}