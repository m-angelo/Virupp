package com.wodadevs.virupp.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wodadevs.virupp.R
import com.wodadevs.virupp.activities.MainActivity.Companion.user_doc
import com.wodadevs.virupp.classes.ExitDialogBox
import kotlinx.android.synthetic.main.activity_wash.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class WashActivity : AppCompatActivity() {
    val db = Firebase.firestore
    val user_db = db.collection("users").document(user_doc!!.id!!)
    val timeleft_ms = 30000.toLong()//30 s
    val interval = 1000.toLong()
    var paused = false
    var timestamp_s = 27
    var counter = 0
    var resumeFromMillis: Long = 0
    var resumename: String =""
    val timer = timer(timeleft_ms,interval,"main")
    val initial_timer = timer(6000,interval,"init")
    val images = listOf<Int>(
        R.drawable.wash1,
        R.drawable.wash2,
        R.drawable.wash3,
        R.drawable.wash4,
        R.drawable.wash5,
        R.drawable.wash6,
        R.drawable.wash7,
        R.drawable.wash8,
        R.drawable.wash9,
        R.drawable.wash10
    )

    fun setImage(image: Int){
        steps_image.setImageDrawable(ContextCompat.getDrawable(applicationContext,image))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_wash)
        setImage(images[counter])
        initial_timer.start()
        btn_skip.setOnClickListener {  initial_timer.onFinish() }
        btn_exit.setOnClickListener {
            DialogBox()

        }
        btn_pause.setOnClickListener {
            paused = true
            makePause()

        }
        btn_unpause.setOnClickListener {
            paused = false
            timer(resumeFromMillis,interval,resumename).start()
            btn_unpause.visibility=View.GONE
            wash_pause_background.visibility=View.GONE

        }


    }
    fun onWashFinish(){
        var sync = false
        user_db.get().addOnSuccessListener { document ->
            val old_streak = document.get("streak").toString().toInt()
            val old_washes =document.get("washes").toString().toInt()
            user_doc!!.streak = old_streak+1
            user_doc!!.washes = old_washes+1
            user_doc!!.last_wash = System.currentTimeMillis()
            user_db.update("streak",old_streak+1)
            user_db.update("washes",old_washes+1)
            user_db.update("last_wash",System.currentTimeMillis())
            sync = true

        }
        doAsync {
            while (!sync){}
            uiThread {
                finish()
            }
        }
    }
    override fun onPause() {
        super.onPause()
        paused = true
        makePause()

    }
    fun makePause(){
        wash_pause_background.visibility=View.VISIBLE
        btn_unpause.visibility=View.VISIBLE

    }

    fun DialogBox(){
        paused = true
        makePause()
        val cdd = ExitDialogBox(this)
        cdd.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        cdd.show()
    }

    override fun onBackPressed() {
        DialogBox()
    }

    private fun timer(millisInFuture:Long,countDownInterval:Long,timer_type:String):CountDownTimer{
        return object: CountDownTimer(millisInFuture,countDownInterval){
            override fun onTick(millisUntilFinished: Long){
                if (timer_type == "init"){
                    if (paused){
                        resumename="init"
                        resumeFromMillis = millisUntilFinished
                        cancel()
                    }
                    var timeleft = (millisUntilFinished/interval).toInt()
                    wash_precounter.text=timeleft.toString()
                }
                else{
                var timeleft = millisUntilFinished
                var timeleft_s =(millisUntilFinished/1000).toFloat()
                if (paused){
                    makePause()
                    resumename="main"
                    resumeFromMillis = millisUntilFinished
                    cancel()
                }else {
                    if (timeleft_s.toInt() - timestamp_s == 0) {
                        Log.d("action", "change img")
                        if (counter < 9) {
                            timestamp_s -= 3
                            counter += 1
                        }
                        setImage(images[counter])
                    }
                    Log.d("time", timeleft_s.toString())
                    Log.d("stamp", timestamp_s.toString())
                    var timepass = (timeleft_ms / 1000 - timeleft_s)
                    var time_percent = ((timepass / timeleft_ms * 100000))
                    timer_text.setText(timeleft_s.toInt().toString())
                    timer_bar.setProgress(time_percent.toInt())
                }}}

            override fun onFinish() {
                if (timer_type == "init"){
                    btn_skip.visibility= View.GONE
                    wash_manual.visibility= View.GONE
                    btn_bg.visibility= View.GONE
                    wash_precounter.visibility= View.GONE
                    wash_precounter_background.visibility= View.GONE
                    timer.start()

                }else {
                    onWashFinish()
                }
            }

    }
}
}





