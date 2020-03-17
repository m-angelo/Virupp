package com.wodadevs.virupp.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.InfoClass
import com.wodadevs.virupp.classes.ShopsClass
import com.wodadevs.virupp.services.LocationService.Companion.latitude
import com.wodadevs.virupp.services.LocationService.Companion.longitude
import kotlinx.android.synthetic.main.info.*
import kotlinx.android.synthetic.main.shops.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.*

class InfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    statsData.add(InfoClass(title=detailed.getJSONObject(entry).get("country").toString(),
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
                    Log.d("LIST",temp.get("country").toString())
                    statsData.add(InfoClass(
                        title=detailed.getJSONObject(y).get("country").toString(),
                        data1 = detailed.getJSONObject(y).get("cases").toString().toInt() ,
                        data2 = detailed.getJSONObject(y).get("recovered").toString().toInt(),
                        data3 = detailed.getJSONObject(y).get("deaths").toString().toInt()))
                    break
                }
            }}
            Log.d("json",statsData[1].title.toString())
            Log.d("json",statsData[1].data1.toString())
            Log.d("json",statsData[1].data2.toString())
            Log.d("json",statsData[1].data3.toString())

            val InfoData  = listOf<InfoClass>(InfoClass(title=countryName,
                data1 = overall.get("cases").toString().toInt() ,
                data2 = overall.get("recovered").toString().toInt(),
                data3 = overall.get("deaths").toString().toInt(),nestedData = statsData))
            uiThread {
                info_views.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = InfoAdapter(InfoData, context)
        }
                loadingscreen.visibility=View.GONE
            }

        }

//

    }

}

