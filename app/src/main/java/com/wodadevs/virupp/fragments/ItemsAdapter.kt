package com.wodadevs.virupp.fragments
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.ItemsClass
import com.wodadevs.virupp.classes.ShopsClass
import kotlinx.android.synthetic.main.item_container.view.*
import kotlinx.android.synthetic.main.shop_container.view.*


class ItemsAdapter(val items : List<ItemsClass>, val context: Context) : RecyclerView.Adapter<ItemHolder>() {

    // Gets the number of animals in the list

    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.item_container, parent, false)
        )
    }

    fun View.disable(){
        getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
        setClickable(false)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val v = holder
        val item = items[position]
        v.name.text = items[position].name
        v.plus.setOnClickListener {
            v.plus.disable()
            v.minus.disable()

        }
        v.minus.setOnClickListener {
           v.plus.disable()
            v.minus.disable()

        }
        if (position == 0){
            if (item.amount <=50){
                 v.state.secondaryProgress = 0
                v.state.progress = item.amount
            }else{
                v.state.secondaryProgress = item.amount
                v.state.progress = 0
            }
        }else{
            if (item.amount >=50){
                v.state.secondaryProgress = 0
                v.state.progress = item.amount
            }else{
                v.state.secondaryProgress = item.amount
                v.state.progress = 0
        }}

    }
}

class ItemHolder (view: View) : RecyclerView.ViewHolder(view) {
    val name = view.item_name
    val pic = view.item_pic
    val state = view.item_bar
    val plus = view.item_plus
    val minus = view.item_minus

}