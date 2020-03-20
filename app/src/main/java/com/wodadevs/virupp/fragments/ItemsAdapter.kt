package com.wodadevs.virupp.fragments
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.wodadevs.virupp.R
import com.wodadevs.virupp.activities.MainActivity.Companion.user_doc
import com.wodadevs.virupp.classes.ItemsClass
import com.wodadevs.virupp.classes.ShopsClass
import kotlinx.android.synthetic.main.item_container.view.*
import kotlinx.android.synthetic.main.shop_container.view.*


class ItemsAdapter(val items : List<ItemsClass>,val context: Context) : RecyclerView.Adapter<ItemHolder>() {

    // Gets the number of animals in the list
    var current_item: String ?=""
    var current_ref: String ?=""
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

    fun disable(holder: ItemHolder, position: Int,addsub: String?=""){
        val v = holder
        val item = items[position]
        var shop_data: ShopsClass ?= null
        v.plus.setClickable(false)
        v.minus.setClickable(false)
        v.plus.setBackgroundResource(R.drawable.plus_gray)
        v.minus.setBackgroundResource(R.drawable.minus_gray)
        val db = Firebase.firestore
        val user_db = db.collection("users").document(user_doc!!.id!!)
        val shop_db =  db.collection("shops").document(item.dbref)
        val user_list = user_doc!!.shops!!.toMutableList()
        current_item = v.name.tag.toString()

        shop_db.get().addOnSuccessListener { document ->
            shop_data = document.toObject<ShopsClass>()
            if(addsub != "" ){
                user_list.add(current_ref!!)
                user_doc!!.shops = user_list.toList()
                user_db
                    .update("shops",user_doc!!.shops )
                    .addOnSuccessListener { Log.d("TAG", "List updated") }
                    .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                user_db
                    .update("last_shop",System.currentTimeMillis())
                    .addOnSuccessListener { Log.d("TAG", "lastshop updated") }
                    .addOnFailureListener { e -> Log.w("TAG", "Error updating lastshop", e) }

                val old_value: Int = document.get(current_item!!).toString().toInt()
                var new_value : Int = 50
                Log.d("item_name",current_item)
                Log.d("item_val",old_value.toString())
                if (old_value > 0 && old_value < 100){
                when(addsub){
                    "+" -> {
                         new_value = old_value+1
                        //firebase read state
                    }
                    "-" -> {
                         new_value = old_value-1
                    }

                }
                setBars(new_value,position,holder)
                shop_db
                    .update(current_item!!,new_value)
                    .addOnSuccessListener { Log.d("TAG", "shop updated") }
                    .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e)

                    }}}
        }

    }
    fun setBars(value: Int,position: Int,holder: ItemHolder){
        val v = holder
        Log.d("bar setted"+v.name.text,value.toString())
        if (position == 0){
            if (value <=50){
                v.state.secondaryProgress = 0
                v.state.progress = value
            }else{
                v.state.secondaryProgress = value
                v.state.progress = 0
            }
        }else{
            if (value >=50){
                v.state.secondaryProgress = 0
                v.state.progress = value
            }else{
                v.state.secondaryProgress = value
                v.state.progress = 0
            }}
    }
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val v = holder
        val item = items[position]
        when(item.name){
            "Kolejka" -> {v.name.tag="queue"}
            "Jedzenie" -> {v.name.tag="jedzenie"}
            "Picie" -> {v.name.tag="picie"}
        }
        current_ref=item.dbref+"_"+v.name.tag
        Log.d("shops",user_doc!!.shops!!.toString())
        Log.d("shops crnt",current_ref)
        if (user_doc!!.shops!!.contains(current_ref!!)){
            disable(holder,position)
        }else{
            v.plus.setOnClickListener {
                disable(holder,position,"+")
                Log.d("click","plus clickinn no more")

            }
            v.minus.setOnClickListener {
                disable(holder,position,"-")
                Log.d("click","minus clickinn no more")

            }
        }
        v.name.text = items[position].name
        setBars(item.amount,position,v)



    }
}

class ItemHolder (view: View) : RecyclerView.ViewHolder(view) {
    val name = view.item_name
    val pic = view.item_pic
    val state = view.item_bar
    val plus = view.item_plus
    val minus = view.item_minus

}