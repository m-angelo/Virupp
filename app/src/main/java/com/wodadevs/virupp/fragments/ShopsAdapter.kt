package com.wodadevs.virupp.fragments

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.ItemsClass
import com.wodadevs.virupp.classes.ShopsClass
import kotlinx.android.synthetic.main.shop_container.view.*

class ShopsAdapter(val items : MutableList<ShopsClass>, val context: Context) : RecyclerView.Adapter<ShopHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(value: ShopsClass, index:Int){
        items.add(value)
        notifyItemInserted(index)
    }
    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ShopHolder {
        var selected_layout: Int = R.layout.shop_container
        if (viewType == items.size){
            selected_layout = R.layout.empty_container
        }
        return ShopHolder(
            LayoutInflater.from(
                context
            ).inflate(selected_layout, parent, false)
        )
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ShopHolder, position: Int) {
        var item = items[position]
        val v = holder
        val dbref = item.dbref
        if (position != items.size){
        val ItemsData = listOf(
            ItemsClass("Queue", item.queue!!,dbref!!),
            ItemsClass("Food", item.jedzenie!!,dbref),
            ItemsClass("Water", item.picie!!,dbref)
        )
        v.street_text?.text = item.street
        v.shop_text?.text = item.name
            var color = R.color.tintRED
            if (item.queue!! <= 33){color = R.color.tintGRN}
            else if  (item.queue!! <= 66){color = R.color.tintYLW}
            v.shop_status?.setColorFilter(ContextCompat.getColor(context,color))
        v.shop_items?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter =
                ItemsAdapter(ItemsData, context)

        }
        v.extend?.setOnClickListener{
            if ( v.shop_items?.visibility==View.VISIBLE){
                 v.shop_items?.visibility=View.GONE}
            else{
                v.shop_items?.visibility=View.VISIBLE
        }
        }
    }}

}

class ShopHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val street_text = view.street_text
    val shop_text = view.shop_text
    val extend = view.expander
    val shop_status = view.status
    val shop_items = view.shop_items
}