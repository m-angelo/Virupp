package com.wodadevs.virupp.fragments
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wodadevs.virupp.R
import com.wodadevs.virupp.api.Common
import com.wodadevs.virupp.api.Places
import com.wodadevs.virupp.api.ShopsApi
import com.wodadevs.virupp.classes.ShopsClass
import com.wodadevs.virupp.services.LocationService.Companion.latitude
import com.wodadevs.virupp.services.LocationService.Companion.longitude
import kotlinx.android.synthetic.main.shops.*
import org.jetbrains.anko.support.v4.intentFor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ShopsFragment : Fragment() {

    private var API_KEY:String = "AIzaSyA-CIQl_R_t_GrdaxtDna9ofTjDaSjNefw"

    private var locationManager : LocationManager? = null

    lateinit var mService:ShopsApi
    lateinit var adapter:ShopsAdapter

    internal lateinit var currentPlace:Places

    private val ShopsData: MutableList<ShopsClass> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.shops, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var latitude = latitude
        var longitude = longitude
        var geocoder = Geocoder(activity, Locale.getDefault())
        var addresses: List<Address> = geocoder.getFromLocation(latitude,longitude , 1)
        var cityName: String = addresses[0].locality.toString()
        city_text.setText(cityName)

        mService = Common.googleApiService

        nearbyShops()

    }

    private fun nearbyShops(){

        val url = getUrl(latitude, longitude)

        mService.getNearbyPlaces(url)
            .enqueue(object : Callback<Places>{
                override fun onResponse(call: Call<Places>?, response: Response<Places>?) {

                    currentPlace = response!!.body()!!

                    if(response.isSuccessful){
                        for (i in 0 until response!!.body()!!.results!!.size){
                            val googlePlace = response.body()!!.results!![i]
                            val lat = googlePlace.geometry!!.location!!.lat
                            val lng = googlePlace.geometry!!.location!!.lng
                            var geocoder = Geocoder(activity, Locale.getDefault())
                            val street = googlePlace.vicinity
                            val placeName = googlePlace.name
                            ShopsData.add(ShopsClass(placeName!!, street!!))

                        }
                        shops_view.apply{
                            layoutManager = LinearLayoutManager(activity)
                            adapter= ShopsAdapter(ShopsData, context!!)
                        }
                    }
                }

                override fun onFailure(call: Call<Places>, t: Throwable) {
                    Toast.makeText(context,""+t.message,Toast.LENGTH_SHORT).show()
                }

            })

    }
    private fun getUrl(latitude: Double, longitude: Double): String {
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=10000")
        googlePlaceUrl.append("&type=grocery_or_supermarket")
        googlePlaceUrl.append("&key=$API_KEY")

        return googlePlaceUrl.toString()
    }

    companion object {
        fun newInstance(): ShopsFragment =
            ShopsFragment()
    }
}