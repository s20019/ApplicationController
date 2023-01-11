package com.example.tabsample2

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.tabsample2.activity.MainActivity

class AlarmService :Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val id = intent?.getIntExtra("id", 0) ?: 0
        //val seconds = intent?.getLongExtra("time", 0)

        Log.d("ALARM", "fire AlarmService#onStartCommand id: $id")

        val pendingIntent = PendingIntent.getActivity(
            this, id,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(android.R.drawable.ic_media_play)
            setContentTitle("時間になりました")
            setContentText("いますぐ手を止めましょう")
            priority = NotificationCompat.PRIORITY_MAX
            setCategory(NotificationCompat.CATEGORY_CALL)
            setFullScreenIntent(pendingIntent, true)
            setWhen(System.currentTimeMillis())
        }.build()
        startForeground(id, notification)
        applicationContext.startActivity(
            Intent(applicationContext, MainActivity::class.java).setFlags(FLAG_ACTIVITY_NEW_TASK)
        )
        return START_NOT_STICKY
    }
}
