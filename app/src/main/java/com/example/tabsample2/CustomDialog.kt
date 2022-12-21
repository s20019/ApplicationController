package com.example.tabsample2

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.tabsample2.activity.MainActivity
import java.util.*

class CustomDialog(private val title: String,
                   private val isRunning: Boolean,
                   private val okSelected: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())

        // ダイアログに自動的に入る背景を透過させる処理
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // カスタムしたダイアログレイアウトを適用する
        dialog.setContentView(R.layout.custom_dialog)

        val pwdInput = dialog.findViewById<EditText>(R.id.pwdInputField)
        val dialogTitle = dialog.findViewById<TextView>(R.id.dialogTitle)
        val selectionPositive = dialog.findViewById<TextView>(R.id.selectionOK)
        val selectionNegative = dialog.findViewById<TextView>(R.id.selectionCancel)

        // タイトルを設定
        dialogTitle.text = title

        val sharedPref = activity?.getSharedPreferences("DataStore", Context.MODE_PRIVATE)

        // 「 OK 」がタップされた時
        selectionPositive.setOnClickListener {
            // アラームがスタートしているとき（止めるためにパスワード認証が必要）
            if (isRunning) {
                val password = sharedPref?.getString("Password", "")

                // 入力したパスワードが正しければアラームをストップさせる処理が動く
                if (pwdInput.text.toString() == password) {
                    okSelected()
                }

                // 正しくなければメッセージで知らせる
                else
                    Toast.makeText(context, "パスワードが違います", Toast.LENGTH_SHORT).show()
            }
            // アラームがスタートしていないとき（開始するためにパスワードの設定が必要）
            else {
                // パスワードを SharedPreference に登録
                sharedPref?.edit()
                    ?.putString("Password", pwdInput.text.toString())
                    ?.apply()

                okSelected()
            }
            dialog.dismiss()
        }

        // 「キャンセル」がタップされた時
        selectionNegative.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }
}