package com.example.tabsample2.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tabsample2.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private var switchState = true              // スイッチの状態を記憶する変数

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // アクションバーに戻るボタンを表示する処理
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPref = getSharedPreferences("DataStore", Context.MODE_PRIVATE)

        // 保存されたスイッチの状態を switchState に代入
        switchState = sharedPref.getBoolean("DataBoolean", true)

        // 保存されたスイッチの状態を参照する
        binding.pwdSwitch.isChecked = switchState

        binding.pwdSwitch.setOnCheckedChangeListener { _, isChecked ->
            // SharedPreference に書き込む
            sharedPref.edit()
                // スイッチがタップされた時の値を書き込んでいる
                .putBoolean("DataBoolean", isChecked)
                .apply()
        }
    }

    // 戻るボタンの挙動
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        return super.onSupportNavigateUp()
    }
}