package com.wodadevs.virupp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wodadevs.virupp.R
import com.wodadevs.virupp.activities.MainActivity.Companion.user_doc
import com.wodadevs.virupp.activities.WashActivity
import kotlinx.android.synthetic.main.hands.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HandsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()
        onUpdateContent()
    }
    fun onUpdateContent(){
        val last_wash = user_doc!!.last_wash //user_doc!!.last_wash
        val delta_wash = System.currentTimeMillis() - last_wash!!
        val delta_hours = (delta_wash / (1000 * 60 * 60)).toInt()
        val delta_days = (delta_wash / (1000 * 60 * 60 * 24)).toInt()
        wash_rank.text="Rank\n${999}st"
        wash_streak.text = "Streak\n${user_doc!!.streak}"
        wash_count.text = "Washes\n${user_doc!!.washes}"

        if (delta_days > 31){
            wash_last.text = "Wash your hands!"
            wash_last.background=ContextCompat.getDrawable(context!!,R.drawable.wash_red)
        }
        else if (delta_days  > 0){
            wash_last.text = "Last wash ${delta_days} days ago"
            wash_last.background=ContextCompat.getDrawable(context!!,R.drawable.wash_red)
        }else if (delta_hours > 0) {
            wash_last.text = "Last wash ${delta_hours} hours ago"
            wash_last.background=ContextCompat.getDrawable(context!!,R.drawable.wash_yellow)
        }else{
            wash_last.text = "Last wash less than hour ago"
            wash_last.background=ContextCompat.getDrawable(context!!,R.drawable.wash_green)
        }
    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.hands, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doAsync {
            while (true) {
                if (user_doc != null) {
                    break
                }
            }
            uiThread {
                loading_screen.visibility=View.GONE
                start_wash.setOnClickListener {
                    val intent = Intent(context, WashActivity::class.java)
                    context?.startActivity(intent)
                }
                wash_last.visibility=View.VISIBLE
                onUpdateContent()
        }}

    }

    }

