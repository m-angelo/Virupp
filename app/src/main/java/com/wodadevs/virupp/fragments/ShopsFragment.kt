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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.wodadevs.virupp.R
import com.wodadevs.virupp.api.Common
import com.wodadevs.virupp.api.Places
import com.wodadevs.virupp.api.ShopsApi
import com.wodadevs.virupp.classes.ShopsClass
import com.wodadevs.virupp.services.LocationService.Companion.city
import com.wodadevs.virupp.services.LocationService.Companion.latitude
import com.wodadevs.virupp.services.LocationService.Companion.longitude
import kotlinx.android.synthetic.main.shops.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ShopsFragment : Fragment() {

    private var API_KEY:String ?= ""
    private var locationManager : LocationManager? = null

    lateinit var mService:ShopsApi
    lateinit var adapter:ShopsAdapter
    var cityName:String ?=""
    var shop_doc:ShopsClass ?= null
    var execute = 1
    var len = 999

    internal lateinit var currentPlace:Places

    private val ShopsSeries: MutableList<ShopsClass> = ArrayList()


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
        API_KEY = getString(R.string.googlemaps_api_key)
        mService = Common.googleApiService
        city_text.setText("")
        nearbyShops()
        doAsync {
            while(latitude==0.0 && longitude == 0.0){}
            var latitude = latitude
            var longitude = longitude
            var geocoder = Geocoder(activity, Locale.getDefault())
            var addresses: List<Address> = geocoder.getFromLocation(latitude,longitude , 1)
            cityName = addresses[0].locality.toString()
            uiThread {
                city_text.setText(cityName)
            }
            while(execute<len){}
            uiThread {
                shops_view.apply{
                    layoutManager = LinearLayoutManager(activity)
                    adapter= ShopsAdapter(ShopsSeries, context!!)
                }
                shop_loadingscreen.visibility=View.GONE
            }
        }

    }

    private fun nearbyShops(){

        val url = getUrl(latitude, longitude)

        mService.getNearbyPlaces(url)
            .enqueue(object : Callback<Places>{
                override fun onResponse(call: Call<Places>?, response: Response<Places>?) {

                    currentPlace = response!!.body()!!

                    if(response.isSuccessful){
                        len = currentPlace.results!!.size-1
                        for (i in 0 until currentPlace.results!!.size-1){
                            Log.d("amount reponses", response!!.body()!!.results!!.size.toString())
                            val googlePlace = response.body()!!.results!![i]
                            val street = googlePlace.vicinity
                            val placeName = googlePlace.name
                            val glen = googlePlace.geometry.toString()
                            Log.d("lengoogle",glen)
                            val placeid = placeName!! + "_" + street!!

                            val db = Firebase.firestore
                            val shop_db = db.collection("shops").document(placeid)
                            shop_db
                                .get()
                                .addOnSuccessListener { document ->
                                    if (document.data != null) {
                                        Log.d("receive data","${document.data}")
                                        shop_doc = document.toObject<ShopsClass>()
                                    } else {
                                        Log.d("send data","shop data")
                                        val shop_data = hashMapOf(
                                            "name" to placeName!!.substringBefore(" "),
                                            "street" to street!!.substringBefore(","),
                                            "queue" to 50,
                                            "jedzenie" to 50,
                                            "picie" to 50
                                        )
                                        //FIND A BETTER WAY FOR THAT
                                        shop_doc = ShopsClass(
                                            shop_data.get("name").toString(),
                                            shop_data.get("street").toString(),
                                            shop_data.get("queue").toString().toInt(),
                                            shop_data.get("jedzenie").toString().toInt(),
                                            shop_data.get("picie").toString().toInt()

                                            )
                                        shop_db.set(shop_data)
                                    }
                                    shop_doc!!.dbref = placeid
                                    ShopsSeries.add(shop_doc!!)
                                    Log.d("current i","${execute} and size is ${len}")
                                    execute = execute + 1

                                }
                        }


                    }else{
                        Log.d("amount reponses", "fail")

                    }

                }

                override fun onFailure(call: Call<Places>, t: Throwable) {
                    Toast.makeText(context,""+t.message,Toast.LENGTH_SHORT).show()
                }

            })

    }
    private fun getUrl(latitude: Double, longitude: Double): String {
        Log.d("location detail", "?location=$latitude,$longitude")
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=15000")
        googlePlaceUrl.append("&type=grocery_or_supermarket")
        googlePlaceUrl.append("&key=$API_KEY")
        return googlePlaceUrl.toString()
    }

    companion object {
        fun newInstance(): ShopsFragment =
            ShopsFragment()
    }
}