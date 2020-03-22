package com.example.timer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity()
    , View.OnClickListener {
    /**
     * タイマー
     */
    private var mTimer: Timer? = null
    /**
     * タイマー初期値
     */
    private val initial = 0.0
    /**
     * タイマーの時間
     */
    private var mTimeSec = initial
    /**
     * ハンドラ
     */
    private var mHandler = Handler()
    /**
     * スタートボタンテキスト
     */
    private val start = "▷"
    /**
     * ストップボタンテキスト
     */
    private val stop = "Ⅱ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStartStop.setOnClickListener(this)
        btReset.setOnClickListener(this)

        btStartStop.text = start
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btStartStop -> {

                if (btStartStop.text == start) {
                    // ストップボタンをセット
                    btStartStop.text = stop
                } else {
                    // スタートボンタンをセット.
                    // タイマーを終了するが、表示は初期化しない.
                    btStartStop.text = start
                    mTimer!!.cancel()
                    mTimer = null
                    return
                }

                mTimer = Timer()
                mTimer!!.schedule(object : TimerTask() {
                    override fun run() {
                        mTimeSec += 0.1
                        mHandler.post {
                            tvTimer.text = String.format("%.1f", mTimeSec)
                        }
                    }
                }, 100, 100) // 100ms経過する毎に100msのループを設定する
            }


            R.id.btReset -> {
                if (mTimer != null) {
                    // 現在スケジュールされているタスクを破棄してタイマーを終了する
                    mTimer!!.cancel()
                    mTimer = null
                }
                // 表示を初期化.
                btStartStop.text = start
                mTimeSec = initial
                tvTimer.text = String.format("%.1f", mTimeSec)
            }
        }
    }
}
