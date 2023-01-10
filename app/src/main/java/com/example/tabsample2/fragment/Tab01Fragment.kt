package com.example.tabsample2.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.tabsample2.RecyclerAdapter
import com.example.tabsample2.databinding.Tab01FragmentBinding

class Tab01Fragment : Fragment() {
    private var _binding: Tab01FragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = Tab01FragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecyclerAdapter(createList())
        recyclerView = binding.recyclerList

        // リストに区切り線を入れる処理
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // リストのアイテムがタップされたときの処理
        adapter.setOnAlarmListClickListener(
            object : RecyclerAdapter.OnAlarmListClickListener {
                override fun onItemClick(alarmData: AlarmData) {
                    // タップされたアイテムの時間と文字列を SharedPreference に記録する処理
                    val sharedPref = activity?.getSharedPreferences("DataStore", Context.MODE_PRIVATE)

                    sharedPref?.edit()
                        ?.putString("AlarmText", alarmData.mText)     // AlarmText というキー名で mText を記録
                        ?.putInt("AlarmTime", alarmData.mTime)        // AlarmTime というキー名で mTime を記録
                        ?.apply()

                    findNavController().navigate(R.id.action_tab03_to_first)
                }
            }
        )
    }

    // アラームリストを作成するメソッド
    fun createList(): MutableList<AlarmData> {
        val resultList: MutableList<AlarmData> = ArrayList()

        var mHou = 0            // 時間
        var mMin = 0            // 分
        var hHou = 0
        var mTime = 0           // AlarmDataの mTime の部分
        var mText: String       // AlarmDataの mText の部分
        var hTime = 0           // AlarmDataの hTime の部分
        var hText: String       // AlarmDataの hText の部分

        var i = 1
        while (i < 13) {        // １２回繰り返し
            mMin += 15
            mTime += 900
            hTime += 3600
            hHou++
            hText = "${hHou}:00:00"

            if (i % 4 == 0) {   // 分が繰り上がるタイミングで、繰り上がりの処理
                mHou++          // 繰り上がりのため、１増える
                mMin = 0        // 繰り上がりのため、０になる

                mText = "${mHou}:${mMin}0:00"    // 桁数を合わせるため min の後ろに０を追加
                resultList.add(AlarmData(mTime, mText, hTime, hText))
            }
            else {
                mText = "${mHou}:${mMin}:00"
                resultList.add(AlarmData(mTime, mText, hTime, hText))
            }
            i++
        }
        Log.i("resultList", "$resultList")
        return resultList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}