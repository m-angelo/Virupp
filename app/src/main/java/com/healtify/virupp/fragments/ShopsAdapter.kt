package com.healtify.virupp.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healtify.virupp.R
import com.healtify.virupp.classes.Shops_class
import kotlinx.android.synthetic.main.shop_container.view.*

class ShopsAdapter(val items : List<Shops_class>, val context: Context) : RecyclerView.Adapter<ShopHolder>() {

    // Gets the number of animals in the list

    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ShopHolder {
        return ShopHolder(LayoutInflater.from(context).inflate(R.layout.shop_container, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ShopHolder, position: Int) {
        holder?.street_text?.text = items[position].str
        holder?.shop_text?.text = items[position].nm
    }
}

class ShopHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val street_text = view.street_text
    val shop_text = view.shop_text
}