package com.wodadevs.virupp.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.ItemsClass
import com.wodadevs.virupp.classes.ShopsClass
import kotlinx.android.synthetic.main.shop_container.view.*

class ShopsAdapter(val items : MutableList<ShopsClass>, val context: Context) : RecyclerView.Adapter<ShopHolder>() {

    // Gets the number of animals in the list

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(value: ShopsClass, index:Int){
        items.add(value)
        notifyItemInserted(index)
    }
    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ShopHolder {
        return ShopHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.shop_container, parent, false)
        )
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ShopHolder, position: Int) {
        val ItemsData = listOf(
            ItemsClass("Papier", 10),
            ItemsClass("Mięso", 1),
            ItemsClass("Słodycze", 4),
            ItemsClass("Papier", 10)
        )
        holder?.street_text?.text = items[position].str
        holder?.shop_text?.text = items[position].nm
        holder?.shop_items?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter =
                ItemsAdapter(ItemsData, context)

        }
        holder?.extend?.setOnClickListener{
            if ( holder?.shop_items?.visibility==View.VISIBLE){
                 holder?.shop_items?.visibility=View.GONE}
            else{
                holder?.shop_items?.visibility=View.VISIBLE
        }
        }
    }

}

class ShopHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val street_text = view.street_text
    val shop_text = view.shop_text
    val extend = view.expander
    val shop_items = view.shop_items
}