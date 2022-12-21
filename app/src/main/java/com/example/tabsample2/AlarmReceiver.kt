package com.example.tabsample2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver :BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("broadcastCheck" , "ぶろーどきゃすと")
        val service = Intent(context, AlarmService::class.java).apply {
            putExtras(intent)
        }

        context.startService(service)
    }
}
