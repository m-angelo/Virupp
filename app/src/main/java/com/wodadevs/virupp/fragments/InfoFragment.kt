package com.wodadevs.virupp.fragments

import android.content.Context
import android.icu.text.IDNA
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.InfoClass
import com.wodadevs.virupp.services.LocationService.Companion.latitude
import com.wodadevs.virupp.services.LocationService.Companion.longitude
import kotlinx.android.synthetic.main.info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.textColor
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.*

class InfoFragment : Fragment() {
    val db = Firebase.firestore
    val InfoData  = mutableListOf<InfoClass>()
    val MitData  = mutableListOf<InfoClass>()
    val NewsData  = mutableListOf<InfoClass>()
    var AdapterData = mutableListOf<InfoClass>()
    var cont = false
    var checked = "NEWS"
    var this_adapter:InfoAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.info, container, false)

    fun onArticleGet(){
        val articles_db =  db.collection("articles")
        articles_db
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var article_obj = document.toObject<InfoClass>()
                    Log.d("type",article_obj.type)
                    when(article_obj.type){
                        "MIT" -> {MitData.add(article_obj) }
                        "INFO" -> {InfoData.add(article_obj) }
                        "NEWS" -> {NewsData.add(article_obj) }
                    }


                }
                MitData.sortBy { it.index }
                InfoData.sortBy { it.index }
                NewsData.sortBy { it.index }
                cont = true
            }
    }

    fun onLocationGet(view: View){
        doAsync {
            val overall = JSONObject( URL("https://corona.lmao.ninja/all").readText())
            val detailed = JSONArray(( URL("https://corona.lmao.ninja/countries").readText()))
            var geocoder = Geocoder(activity, Locale.ENGLISH)
            var addresses: List<Address> = geocoder.getFromLocation(latitude,longitude , 1)
            var countryName: String = addresses[0].countryName.toString()

            val countries = mutableListOf<String>()
            val mostcases = mutableListOf<Int>()
            for (entry in 0..(detailed.length()-1)){
                countries.add(detailed.getJSONObject(entry).get("country").toString())
                mostcases.add(detailed.getJSONObject(entry).get("cases").toString().toInt())
            }
            val sortedcases = mostcases
            val used = mutableListOf<String>()
            val statsData  = mutableListOf<InfoClass>()
            for (entry in 0..(countries.size-1)){
                if (countries[entry] == countryName){
                    used.add(countryName)
                    statsData.add(
                        InfoClass(title=detailed.getJSONObject(entry).get("country").toString(),
                        data1 = detailed.getJSONObject(entry).get("cases").toString().toInt() ,
                        data2 = detailed.getJSONObject(entry).get("recovered").toString().toInt(),
                        data3 = detailed.getJSONObject(entry).get("deaths").toString().toInt()))
                }
            }
            sortedcases.sort()
            for (x in 0..2){
                for (y in 0..mostcases.size-1){
                    if (sortedcases[x]==mostcases[y] && countries[y] !in used){
                        var temp = detailed.getJSONObject(y)
                        used.add(countries[y])
                        statsData.add(InfoClass(
                            title=detailed.getJSONObject(y).get("country").toString(),
                            data1 = detailed.getJSONObject(y).get("cases").toString().toInt() ,
                            data2 = detailed.getJSONObject(y).get("recovered").toString().toInt(),
                            data3 = detailed.getJSONObject(y).get("deaths").toString().toInt()))
                        break
                    }
                }}

            NewsData.add(
                InfoClass(title=countryName,
                    data1 = overall.get("cases").toString().toInt() ,
                    data2 = overall.get("recovered").toString().toInt(),
                    data3 = overall.get("deaths").toString().toInt(),
                    nestedData = statsData,
                    index=0)
            )

            onArticleGet()

            while(!cont){}

            uiThread {
                AdapterData=NewsData
                this_adapter= InfoAdapter(AdapterData,"NEWS", context!!)
                info_views.apply{
                    layoutManager = LinearLayoutManager(activity)
                    adapter = this_adapter
                }
                radio_news.setOnClickListener{onRadioChange(radio_news,info_views)}
                radio_info.setOnClickListener{onRadioChange(radio_info,info_views)}
                radio_mit.setOnClickListener{onRadioChange(radio_mit,info_views)}
                loadingscreen.visibility=View.GONE
            }

        }
    }
    fun onRadioChange(v: View,recyclerv: RecyclerView) {
        if (v is Button) {
            // Is the button now checked?
            val check = (checked == v.tag)
            Log.d("type_press",v.tag.toString())
            checked = v.tag.toString()
            val color_dis = R.color.disable_btn
            val color_activ = R.color.colorPrimary
            radio_info.textColor= ContextCompat.getColor(context!!,color_dis)
            radio_mit.textColor= ContextCompat.getColor(context!!,color_dis)
            radio_news.textColor= ContextCompat.getColor(context!!,color_dis)
            v.textColor = ContextCompat.getColor(context!!,color_activ)
            when (v.tag) {
                "MITY" ->
                    if (!check) {
                        Log.d("type_press_check",v.tag.toString())
                        AdapterData = MitData
                    }
                "INFO" ->
                    if (!check) {
                        Log.d("type_press_check",v.tag.toString())
                        AdapterData = InfoData
                    }
                "NEWS" ->
                    if (!check) {
                        Log.d("type_press_check",v.tag.toString())
                        AdapterData = NewsData
                    }
            }
            if (!check) {
                val swappedAdapter = InfoAdapter(AdapterData,v.tag.toString(), context!!)
                recyclerv.setAdapter(swappedAdapter);
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       doAsync {
           while (true) {
               if (latitude != 0.0 || longitude != 0.0){
                   break
               }
           }
           onLocationGet(view)
       }


//

    }

}

