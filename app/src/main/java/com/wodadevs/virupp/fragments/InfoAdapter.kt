package com.wodadevs.virupp.fragments

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.TextClassifier.TYPE_EMAIL
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.InfoClass
import kotlinx.android.synthetic.main.head_container.view.*
import kotlinx.android.synthetic.main.info_container.view.*


class InfoAdapter(val items : List<InfoClass>,val list_type: String, val context: Context) : RecyclerView.Adapter<InfoHolder>() {

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

        if (viewType == items.size){
            selected_layout = R.layout.empty_container
            Log.d("layout","empty")}
        else if(viewType == 0 && list_type == "NEWS"){
            selected_layout = R.layout.head_container
            Log.d("layout","graph")}
        else{selected_layout = R.layout.info_container
            Log.d("layout","art")}



        return InfoHolder(
            LayoutInflater.from(
                context
            ).inflate(selected_layout, parent, false)
        )
    }
    fun Graph_loader(holder: InfoHolder, position: Int){
        val data =items.get(position)
        var v = holder?.v
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
                    v.expander.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.expand))
                } else {
                    v.MoreContent.visibility = View.VISIBLE
                    v.expander.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.collapse))
                }
            }
        }
        
    }
    fun Article_loader(holder: InfoHolder, position: Int){
        val data =items.get(position)
        var v = holder?.v
        val head = data.title
        val body = data.short
        val full = data.long
        Log.d("tag",v.tag.toString())
        v.article_title.text = head
        if (body==""){
            v.article_start.height=0
        }
        v.article_start.text = body
        v.article_end.text = full
        v.article_expander.setOnClickListener {
            if (v.article_end.visibility == View.VISIBLE) {
                v.article_end.visibility = View.GONE
                v.expander.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.expand))

            } else {
                v.article_end.visibility = View.VISIBLE
                v.expander.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.collapse))

            }}

    }
    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: InfoHolder, position: Int) {
        if (position != items.size){
        val data: InfoClass
            Log.d("position",position.toString())
        var v = holder?.v
            if (list_type == "NEWS"  && position == 0) {
            Graph_loader(holder,position)
            }
        else if (position != items.size){
            Article_loader(holder,position)
        }}
    }

}

class InfoHolder (view: View) : RecyclerView.ViewHolder(view) {
    val v = view
}