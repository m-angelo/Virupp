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
            val countries = mutableListOf<String>()
            for (entry in 1..(detailed.length()-1)){
                countries.add(detailed.getJSONObject(entry).get("country").toString())
        }
            Log.d("json",countries.toString())
            var geocoder = Geocoder(activity, Locale.getDefault())
            var addresses: List<Address> = geocoder.getFromLocation(latitude,longitude , 1)
            var countryName: String = addresses[0].countryName.toString()
            val InfoData  = listOf<InfoClass>(InfoClass(title=countryName,
                data1 = overall.get("cases").toString().toInt() ,
                data2 = overall.get("recovered").toString().toInt(),
                data3 = overall.get("deaths").toString().toInt()))
            uiThread {
                info_views.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter =
                InfoAdapter(InfoData, context)
        }
            }

        }

//

    }

}

