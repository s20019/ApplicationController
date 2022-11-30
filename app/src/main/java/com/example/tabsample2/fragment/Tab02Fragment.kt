package com.example.tabsample2.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabsample2.AlarmData
import com.example.tabsample2.R
import com.example.tabsample2.RecyclerAdapter2
import com.example.tabsample2.databinding.Tab02FragmentBinding

class Tab02Fragment : Fragment() {
    private var _binding: Tab02FragmentBinding? = null
    private val binding get() = _binding!!
    private val tab01Fragment get() = Tab01Fragment()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = Tab02FragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecyclerAdapter2(tab01Fragment.createList())
        recyclerView = binding.recyclerList2

        // リストに区切り線を入れる処理
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.setOnAlarmListClickListener2(
            object : RecyclerAdapter2.OnAlarmListClickListener2 {
                override fun onItemClick(alarmData: AlarmData) {
                    val sharedPref = activity?.getSharedPreferences("DataStore", Context.MODE_PRIVATE)

                    sharedPref?.edit()
                        ?.putString("AlarmText", alarmData.hText)     // AlarmText というキー名で hText を記録
                        ?.putInt("AlarmTime", alarmData.hTime)        // AlarmTime というキー名で hTime を記録
                        ?.apply()

                    findNavController().navigate(R.id.action_tab03_to_first)
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}