package com.wodadevs.virupp.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.ExitDialogBox
import kotlinx.android.synthetic.main.activity_wash.*


class WashActivity : AppCompatActivity() {
    val timeleft_ms = 30000.toLong() //30 s
    var running = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_wash)
        exit_btn.setOnClickListener {
            DialogBox()
        }
        val timer = object: CountDownTimer(timeleft_ms, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var timeleft = millisUntilFinished
                var timeleft_s =(millisUntilFinished/1000).toFloat()
                var timepass = (timeleft_ms/1000 - timeleft_s).toFloat()
                var time_percent = ((timepass / timeleft_ms * 100000) )
                timer_text.setText(timeleft_s.toInt().toString())
                timer_bar.setProgress(time_percent.toInt())
            }

            override fun onFinish() {
            }

        }
        timer.start()

        fun StartStop(){
            running = !running
        }

    }

    fun DialogBox(){
        val cdd = ExitDialogBox(this)
        cdd.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        cdd.show()
    }

    override fun onBackPressed() {
        DialogBox()
    }

}


