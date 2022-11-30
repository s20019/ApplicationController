package com.example.tabsample2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val alarmDataList: MutableList<AlarmData>) : RecyclerView.Adapter<ViewHolder>() {

    // リスナーを格納する変数を定義
    private lateinit var listener: OnAlarmListClickListener

    // インターフェースを作成
    interface OnAlarmListClickListener {
        fun onItemClick(alarmData: AlarmData)
    }

    // リスナーをセット
    fun setOnAlarmListClickListener(listener: OnAlarmListClickListener) {
        this.listener = listener
    }

    // １行分のレイアウトを生成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_list, parent, false)
        return ViewHolder(itemView)
    }

    // ViewHolderを結ぶ役割
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarmData = alarmDataList[position]

        holder.timeList.text = alarmData.mText

        // セルのクリックイベントにリスナーをセット
        holder.itemView.setOnClickListener {
            // セルがクリックされたときにインターフェースの処理が実行される
            listener.onItemClick(alarmData)
        }
    }

    // リストのアイテムをカウントし、その分だけのレイアウトをクリエイトする
    override fun getItemCount(): Int = alarmDataList.size
}