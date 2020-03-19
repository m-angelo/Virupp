package com.wodadevs.virupp.fragments

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.TextClassifier.TYPE_EMAIL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.InfoClass
import kotlinx.android.synthetic.main.head_container.view.*
import kotlinx.android.synthetic.main.info_container.view.*


class InfoAdapter(val items : List<InfoClass>, val context: Context) : RecyclerView.Adapter<InfoHolder>() {

    // Gets the number of animals in the list

    override fun getItemCount(): Int {
        return items.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): InfoHolder {
        var selected_layout: Int = R.layout.info_container
        if (viewType == items.size){ selected_layout = R.layout.empty_container }
        else if(viewType == 0 ){selected_layout = R.layout.head_container}
        else{selected_layout = R.layout.info_container}
        return InfoHolder(
            LayoutInflater.from(
                context
            ).inflate(selected_layout, parent, false)
        )
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: InfoHolder, position: Int) {
        val data =items.get(position)
        var v = holder?.v
        if (position == 0) {
            //screen.label.text = data.title
            if (data.nestedData!!.isNotEmpty()) {
                v.label.text = "Global"
                val recovered = data.data2.toFloat()
                val deaths = data.data3.toFloat()
                val sum = data.data1.toFloat()
                val active = sum - deaths - recovered
                val proc_recovered = ((recovered / sum) * 100).toInt()
                val proc_cases = ((active / sum) * 100).toInt() + proc_recovered
                v.graph_bar.setProgress(proc_recovered)
                v.graph_bar.secondaryProgress = proc_cases
                v.amountCases.text = sum.toInt().toString()
                v.amountDeaths.text = deaths.toInt().toString()
                v.amountRecovered.text = recovered.toInt().toString()
                v.MoreContent.apply {
                    layoutManager = LinearLayoutManager(
                        holder.itemView.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = StatsAdapter(data.nestedData, context)
                }
                v.expander.setOnClickListener {
                    if (v.MoreContent.visibility == View.VISIBLE) {
                        v.MoreContent.visibility = View.GONE
                    } else {
                        v.MoreContent.visibility = View.VISIBLE
                    }
                }
            }}
        else if (position != items.size){
            val head = data.title
            val body = data.short
            val full = data.long
            v.article_title.text = head
            v.article_start.text = body
            v.article_end.text = full
            v.article_expander.setOnClickListener {
                if (v.article_end.visibility == View.VISIBLE) {
                    v.article_end.visibility = View.GONE
                } else {
                    v.article_end.visibility = View.VISIBLE
                }}
        }
    }

}

class InfoHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val v = view
}