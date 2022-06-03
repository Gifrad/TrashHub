@file:Suppress("DEPRECATION")

package com.capstone.project.trashhub.maps

import android.os.AsyncTask
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class GetNearbyPlaces : AsyncTask<Any, String, String>() {
    private lateinit var googleplaceData: String
    private lateinit var url: String
    private lateinit var mMap: GoogleMap
//    private lateinit var latLng : LatLng

    override fun doInBackground(vararg objects: Any): String {
        /*if(objects[0] is GoogleMap)
            mMap = objects[0] as GoogleMap
        if(objects[1] is String)
            url = objects[1] as String*/
        /*if(objects[2] is LatLng)
            latLng = objects[2] as LatLng*/
        mMap = objects[0] as GoogleMap
        url = objects[1] as String
        val downloadUrl = DownloadUrl()
        try {
            googleplaceData = downloadUrl.readTheURL(url)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return googleplaceData
    }

    override fun onPostExecute(s: String) {
        val nearByPlacesList: List<HashMap<String, String>?>
        val dataParser = DataParser()
        nearByPlacesList = dataParser.parse(s)
        displayNearbyPlaces(nearByPlacesList)
    }

    private fun displayNearbyPlaces(nearByPlacesList: List<HashMap<String, String>?>) {
        for (i in nearByPlacesList.indices) {
            val markerOptions = MarkerOptions()
            val googleNearbyPlace = nearByPlacesList[i]
            val nameOfPlace = googleNearbyPlace!!["place_name"]
            val vicinity = googleNearbyPlace["vicinity"]
            val lat = googleNearbyPlace["lat"]!!.toDouble()
            val lng = googleNearbyPlace["lng"]!!.toDouble()
            val latLng = LatLng(lat, lng)
            markerOptions.position(latLng)
            markerOptions.title("$nameOfPlace : $vicinity")
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            mMap.addMarker(markerOptions)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
        }
    }
}