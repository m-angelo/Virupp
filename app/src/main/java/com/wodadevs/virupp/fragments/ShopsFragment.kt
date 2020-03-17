package com.wodadevs.virupp.fragments
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.ShopsClass
import com.wodadevs.virupp.services.LocationService.Companion.latitude
import com.wodadevs.virupp.services.LocationService.Companion.longitude
import kotlinx.android.synthetic.main.shops.*
import java.util.*


class ShopsFragment : Fragment() {

    private var locationManager : LocationManager? = null

    private val ShopsData = listOf(
        ShopsClass("Biedronka", "ul Strazacka"),
        ShopsClass("Carrefour", "ul Nosala"),
        ShopsClass("Biedronka", "ul Krzyzowa")

    )
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
        shops_view.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter =
                ShopsAdapter(ShopsData, context)
        }

    }

    companion object {
        fun newInstance(): ShopsFragment =
            ShopsFragment()
    }
}