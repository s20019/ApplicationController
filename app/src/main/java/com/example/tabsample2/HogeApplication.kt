package com.example.tabsample2

import android.app.Application
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

const val CHANNEL_ID = "HOGE_NOTIFICATION"

class HogeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 通知関連の初期化
        createNotificationChannel()
//        initNotification()
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.channel_name)
        val description = getString(R.string.channel_description)
        val importance = NotificationManagerCompat.IMPORTANCE_MAX
        val channel = NotificationChannelCompat.Builder(CHANNEL_ID, importance).apply {
            setDescription(description)
            setName(name)
        }.build()
        NotificationManagerCompat.from(baseContext)
            .createNotificationChannel(channel)
    }
}