package com.example.tabsample2

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    val timeList: TextView = item.findViewById(R.id.text1)
}