package com.healtify.virupp.fragments
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healtify.virupp.R
import com.healtify.virupp.classes.ItemsClass
import com.healtify.virupp.classes.ShopsClass
import kotlinx.android.synthetic.main.item_container.view.*
import kotlinx.android.synthetic.main.shop_container.view.*


class ItemsAdapter(val items : List<ItemsClass>, val context: Context) : RecyclerView.Adapter<ItemHolder>() {

    // Gets the number of animals in the list

    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_container, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder?.name?.text = items[position].name
        holder?.amount?.text = (items[position].amount).toString()

    }
}

class ItemHolder (view: View) : RecyclerView.ViewHolder(view) {
    val name = view.item_name
    val pic = view.item_pic
    val amount = view.item_value

}