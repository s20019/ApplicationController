package com.example.tabsample2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter2(private val alarmDataList: MutableList<AlarmData>) : RecyclerView.Adapter<ViewHolder2>() {

    private lateinit var listener: OnAlarmListClickListener2

    interface OnAlarmListClickListener2 {
        fun onItemClick(alarmData: AlarmData)
    }

    fun setOnAlarmListClickListener2(listener: OnAlarmListClickListener2) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val itemView2 = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_list, parent, false)
        return ViewHolder2(itemView2)
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        val alarmData = alarmDataList[position]

        holder.timeList2.text = alarmData.hText

        holder.itemView.setOnClickListener {
            listener.onItemClick(alarmData)
        }
    }

    override fun getItemCount(): Int = alarmDataList.size
}