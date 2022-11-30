package com.example.tabsample2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.tabsample2.R
import com.example.tabsample2.ViewPagerAdapter
import com.example.tabsample2.databinding.Tab03FramgnetBinding
import com.google.android.material.tabs.TabLayoutMediator

class Tab03Fragment : Fragment() {
    private var _binding: Tab03FramgnetBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    private val tabTitleArray = arrayOf("１５分", "１時間")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = Tab03FramgnetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 作成したAdapterを使ってViewPagerをセット
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager = view.findViewById(R.id.viewpager)
        viewPager.adapter = viewPagerAdapter

        val tablayout = binding.tablayout

        // タブを生成
        TabLayoutMediator(tablayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_tab03_to_first)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}