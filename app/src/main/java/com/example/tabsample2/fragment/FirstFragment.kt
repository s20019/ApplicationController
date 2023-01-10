package com.example.tabsample2.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tabsample2.AlarmReceiver
import com.example.tabsample2.CustomDialog
import com.example.tabsample2.R
import com.example.tabsample2.activity.MainActivity
import com.example.tabsample2.databinding.FragmentFirstBinding
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var isRunning = false               // アラームが動作中かどうかのフラグ
    private var switchState = true              // スイッチの状態を記憶している変数
    private var alarmTime = 0                   // アラームの時間を保持している変数

    private lateinit var am: AlarmManager
    /*
    private val deleteBtn = binding.deleteBtn
    private val endingTime = binding.endingTime
    private val alarmAddBtn = binding.alarmAddBtn
    private val startStopBtn = binding.startStopBtn
    private val alarmONImage = binding.alarmONImage
    private val alarmOFFImage = binding.alarmOFFImage
    private val alarmTimeText = binding.alarmTimeText
     */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        am = requireContext().getSystemService(AlarmManager::class.java)

        // DataStore という名前の SharedPreference を取得
        val sharedPref = activity?.getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            // SharedPreference にあるスイッチの状態の記録を switchState に代入
            switchState = sharedPref.getBoolean("DataBoolean", true)
            // SP に記録されているアラームのテキストを出力
            binding.alarmTimeText.text = sharedPref.getString("AlarmText", null)
            // SP に記録されているアラームの時間を代入
            alarmTime = sharedPref.getInt("AlarmTime", 0)
        }

        // アラームがセットされていれば、スタートボタン、削除ボタンを表示
        if (binding.alarmTimeText.text.isNotEmpty()) {
            binding.startStopBtn.visibility = View.VISIBLE
            binding.deleteBtn.visibility = View.VISIBLE
        }

        // アラーム追加ボタンをタップすると時間選択画面へ遷移する
        binding.alarmAddBtn.setOnClickListener {
            findNavController().navigate(R.id.action_first_to_tab03)
        }

        // タップしたときに画面をクリアする
        binding.deleteBtn.setOnClickListener {
            // 画面遷移先をこのフラグメントにすることで画面が再描画される
            findNavController().navigate(R.id.action_firstFragment_self)
            // アラームテキストをリセット
            binding.alarmTimeText.text = null
            sharedPref?.edit()
                ?.remove("AlarmText")
                ?.apply()
        }

        binding.startStopBtn.setOnClickListener {
            // パスワード認証の設定がオンになっている場合
            if (switchState) {
                // 現在のフラグの値を見てそれに応じたダイアログを表示
                when (isRunning) {
                    // アラームがスタートしているとき
                    true -> {
                        // ラムダ式のため変わった記述になっている
                        val dialog = CustomDialog("パスワードを入力してください",  isRunning) { switchByAlarm(isRunning) }
                        // ダイアログを表示する処理
                        dialog.show(parentFragmentManager, "password_input_dialog")
                    }
                    // アラームがスタートしていないとき パス設定用dialog
                    false -> {
                        val dialog = CustomDialog("パスワード設定", isRunning) { switchByAlarm(isRunning) }
                        dialog.show(parentFragmentManager, "password_setting_dialog")
                    }
                }
            }
            // パスワードの設定がオフの場合
            else {
                switchByAlarm(isRunning)
            }
        }
    }

    // アラームが動いているかどうかに応じて処理を変えるメソッド
    private fun switchByAlarm(isRunning: Boolean) {
        if (isRunning) {             // この関数の引数のisRunning
            binding.startStopBtn.setImageResource(R.drawable.ic_play2)
            this.isRunning = false   // コードの最初の方で定義しているisRunning

            // アラームが動いていない間は終了時間を非表示にする
            binding.endingTime.visibility = View.INVISIBLE
            // アラームの状態を表す画像の切り替え
            binding.alarmONImage.visibility = View.INVISIBLE
            binding.alarmOFFImage.visibility = View.VISIBLE

            // アラームが動いていない間は alarmAddBtn と deleteBtn を有効にする
            binding.alarmAddBtn.isEnabled = true
            binding.deleteBtn.isEnabled = true

            //AlarmCancel()を準備
            stopAlarm(1)

            Toast.makeText(context, "アラームをリセットしました", Toast.LENGTH_SHORT).show()
        }
        else {
            binding.startStopBtn.setImageResource(R.drawable.ic_pause)
            this.isRunning = true

            val time1 = LocalTime.now()                         // 現在の時間を取得
            val time2 = time1.plusMinutes(alarmTime.toLong())   // アラームの時間を現在の時間にプラスして、終了時間を生成する
            val time3 = time2.truncatedTo(ChronoUnit.MINUTES)   // 表示に不要な情報である秒以下の値を切り捨てる
            binding.endingTime.text = time3.toString()          //@赤字の終了時刻

            // アラームが動いている間は終了時間を表示する
            binding.endingTime.visibility = View.VISIBLE
            binding.alarmONImage.visibility = View.VISIBLE
            binding.alarmOFFImage.visibility = View.INVISIBLE

            // アラームが動いている間は alarmAddBtn と deleteBtn を無効にする
            binding.alarmAddBtn.isEnabled = false
            binding.deleteBtn.isEnabled = false

            //AlarmSet()を準備
            val seconds = alarmTime.toLong()
            setAlarm(1,seconds)

            Toast.makeText(context, "アラームがスタートしました", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun setAlarm(id: Int, seconds: Long) {
        val op: PendingIntent = getAlarmPendingIntent(id , seconds)
        val cal: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis() + seconds * 1000
        }
        val viewIntent: PendingIntent = PendingIntent.getActivity(
            requireContext(), op.hashCode(),
            Intent(requireContext(), MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val info: AlarmManager.AlarmClockInfo =
            AlarmManager.AlarmClockInfo(cal.timeInMillis, viewIntent)
        am.setAlarmClock(info, op)
        Log.d("HOGE_ALARM", "setAlarmClock id: $id")
    }

    private fun stopAlarm(id: Int) {
        val op: PendingIntent = getAlarmPendingIntent(id)
        am.cancel(op)
    }

    private fun getAlarmPendingIntent(id: Int, time: Long = 0): PendingIntent = PendingIntent.getBroadcast(
        requireActivity().applicationContext, id,
        Intent(requireActivity().applicationContext, AlarmReceiver::class.java).apply {
            putExtra("id", id)
            putExtra("time", time)
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    ).apply { Log.d("alarmPendIntCheck" , "moved!!") }
}