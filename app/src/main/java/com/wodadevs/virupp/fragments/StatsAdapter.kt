package com.wodadevs.virupp.fragments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.InfoClass
import com.wodadevs.virupp.classes.ItemsClass
import kotlinx.android.synthetic.main.head_container.view.*
import kotlinx.android.synthetic.main.info_container.view.*


class StatsAdapter(val items : List<InfoClass>, val context: Context) : RecyclerView.Adapter<StatsHolder>() {

    override fun getItemCount(): Int {

        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): StatsHolder {
        val selected_layout: Int = R.layout.stats_container
        return StatsHolder(
            LayoutInflater.from(
                context
            ).inflate(selected_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StatsHolder, position: Int) {
        Log.d("items",items[position].title.toString())
        val data =items[position]
        val v = holder?.v
        Log.d("pos",position.toString())
            //screen.label.text = data.title
        Log.d("data",data.title)
        v.label.text = data.title
        val recovered = data.data2.toFloat()
        val deaths = data.data3.toFloat()
        val sum = data.data1.toFloat()
        val active = sum - deaths - recovered
        val proc_recovered = ((recovered/sum)*100).toInt()
        val proc_cases = ((active/sum)*100).toInt() + proc_recovered
        v.graph_bar.setProgress(proc_recovered)
        v.graph_bar.secondaryProgress = proc_cases
        v.amountCases.text = sum.toInt().toString()
        v.amountDeaths.text = deaths.toInt().toString()
        v.amountRecovered.text = recovered.toInt().toString()


    }

}

class StatsHolder (view: View) : RecyclerView.ViewHolder(view) {
    val v = view
}