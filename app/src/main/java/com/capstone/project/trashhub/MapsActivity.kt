@file:Suppress("DEPRECATION")

package com.capstone.project.trashhub

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.capstone.project.trashhub.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapsViewModel: MapsViewModel
//    LAT LONG BANK SAMPAH
    /* private var latitude = 0.0
     private var longitude = 0.0
     private val proximityRadius = 10000
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        supportActionBar?.hide()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupAction()
        setAdapter()


    }

    private fun setupViewModel() {
        mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        mapsViewModel.getBankSampahLocation()
    }

    private fun setAdapter() {
        val userMarkerOptions = MarkerOptions()
        mapsViewModel.listStory.observe(this) {
            for (element in it) {
                Log.d("Data Map: ", element.lat.toString() + " lon =" + element.lon.toString())
                val location = LatLng(element.lat, element.lon)
                val geofenceRadius = 400.0
                userMarkerOptions.position(location)
                userMarkerOptions.title(element.name)
                userMarkerOptions.snippet("\"${element.lat}, ${element.lon}")
                userMarkerOptions.icon(
                    BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN
                    )
                )
                mMap.addMarker(userMarkerOptions)
                mMap.addCircle(
                    CircleOptions()
                        .center(location)
                        .radius(geofenceRadius)
                        .fillColor(Color.GREEN)
                        .strokeColor(Color.GREEN)
                        .strokeWidth(3f)
                )

            }
        }
    }

    private fun setupAction() {
        binding.searchAddress.setOnClickListener {
            val address = binding.locationSearch.text.toString()
            var addressList: List<Address>? = null
            val userMarkerOptions = MarkerOptions()
            if (!TextUtils.isEmpty(address)) {
                val geocoder = Geocoder(this)
                try {
                    addressList = geocoder.getFromLocationName(address, 6)
                    if (addressList != null) {
                        var i = 0
                        while (i < addressList.size) {
                            val userAddress = addressList[i]
                            val latLng = LatLng(userAddress.latitude, userAddress.longitude)
                            userMarkerOptions.position(latLng)
                            userMarkerOptions.title(address)
                            userMarkerOptions.icon(
                                BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_ORANGE
                                )
                            )
                            mMap.addMarker(userMarkerOptions)
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
                            i++
                        }
                    } else {
                        Toast.makeText(this, "Lokasi tidak ada", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Tulis nama lokasi terlebih dahulu", Toast.LENGTH_SHORT)
                    .show()
            }
        }


//        PLACE API KLIK
/*
        val bankSampah = "clothing_store"
        val transferData = arrayOfNulls<Any>(2)
        val getNearbyPlaces = GetNearbyPlaces()

        binding.btnBankSampahTerdekat.setOnClickListener {
            mMap.clear()
            val url = getUrl(latitude , longitude, bankSampah)
            transferData[0] = mMap
            transferData[1] = url
            getNearbyPlaces.execute(mMap,url,transferData)
            Toast.makeText(this, "Mencari Bank Sampah terdekat...", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Menampilkan Bank Samapah...", Toast.LENGTH_SHORT).show()
        }
*/


//      fOR LOOP FAILED 1
        /* addressList = geocoder.getFromLocationName(address, 6)
         if (addressList != null){
             val i = 0
             val x = i < addressList.size
             for (i in x.toString()){
                 val userAddress : Address = addressList[i.code]
                 val latLng = LatLng(userAddress.latitude, userAddress.longitude)
                 mMap.addMarker(
                     MarkerOptions()
                         .position(latLng)
                         .title("My Location")
                         .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                 )
                 mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                 mMap.animateCamera(CameraUpdateFactory.zoomTo(14f))
             }
         }else{
             Toast.makeText(this,"Tidak menemukan lokasi",Toast.LENGTH_SHORT).show()
         }*/

    }

//    fUNGSI UNTUK MEMANGGIL API KEY PLACES

    /*  private fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {
          val googleURL =
              StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
          googleURL.append("location=$latitude,$longitude")
          googleURL.append("&radius=$proximityRadius")
          googleURL.append("&type=$nearbyPlace")
          googleURL.append("&sensor=true")
  //        googleURL.append("&key=" + "AIzaSyCO8pXhlZhFob_DDM-1ZXF8EDk2cdoDeWM")
          Log.d("GoogleMapsActivity", "url = $googleURL")

          return googleURL.toString()
      }*/


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true


        // Add a marker in Sydney and move the camera
        /* val sydney = LatLng(-34.0, 151.0)
         mMap.addMarker(MarkerOptions().position(sydney).title("My Location"))
         mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))*/
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        getMyLocation()
        getMyLastLocation()
    }

    // GetMyLocation
    private val requestPermissionLauncherr =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncherr.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    //  GetMyLastLocation
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyLastLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLastLocation()
                }
                else -> {
                    // No location access granted.
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    showStartMarker(location)
                } else {
                    Toast.makeText(
                        this@MapsActivity,
                        "Location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun showStartMarker(location: Location) {
//       GET NERBY PLACE
        /* latitude = location.latitude
        longitude = location.longitude*/
        val startLocation = LatLng(location.latitude, location.longitude)
        mMap.addMarker(
            MarkerOptions()
                .position(startLocation)
                .title("My Location")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 10f))
    }

}
