package com.wodadevs.virupp.services
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*

class LocationService : Service() {
    companion object {
        var latitude: Double = 0.0
        var longitude: Double = 0.0
        var city: String =""
        var country: String =""
    }
    var fusedLocationClient : FusedLocationProviderClient? = null
    var locationCallback : LocationCallback? = null
    override fun onBind(intent: Intent?): IBinder? {
       return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Log.v("SERVICE","LOCATION START")
        locationCallback = object: LocationCallback(){
        override  fun onLocationResult(locationResult: LocationResult?){
            super.onLocationResult(locationResult)
            val mCurrentLocation = locationResult!!.lastLocation
            latitude = mCurrentLocation!!.latitude
            longitude = mCurrentLocation!!.longitude
            Log.d(TAG!!, "Current Location Latitude: " + latitude + " Longitude: " +
                    longitude)


        }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        requestLocation()
        return super.onStartCommand(intent, flags, startId)
    }

    private  fun requestLocation(){
        val locationRequest = LocationRequest()
        locationRequest.setInterval(180000)
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        fusedLocationClient?.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())

    }
}