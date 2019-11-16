package com.example.pauloandroidcourse

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pauloandroidcourse.model.EarthQuake
import com.example.pauloandroidcourse.ui.CustomInfoWindow
import com.example.pauloandroidcourse.util.Constants

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import java.lang.Exception
import java.text.DateFormat
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    var locationManager: LocationManager? = null
    var locationListener: LocationListener? = null
    var listener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {

        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

        }

        override fun onProviderEnabled(p0: String?) {

        }

        override fun onProviderDisabled(p0: String?) {

        }

    }

    lateinit var dialogBuilder : AlertDialog.Builder
    lateinit var dialog: AlertDialog

    var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        queue = Volley.newRequestQueue(this@MapsActivity)
        getEarthQuakes()
    }

    private fun getEarthQuakes() {
        var earthQuake: EarthQuake? = null
        var json = JsonObjectRequest(Request.Method.GET, Constants.URL, Response.Listener {

            try {
                var features: JSONArray = it.getJSONArray("features")
                for (i in 0 until Constants.LIMIT) {
                    var props = features.getJSONObject(i).getJSONObject("properties")
                    var geometry = features.getJSONObject(i).getJSONObject("geometry")
                    var coordinates = geometry.getJSONArray("coordinates")
                    var lon: Double = coordinates.getDouble(0)
                    var lat: Double = coordinates.getDouble(1)
                    var place = props.getString("place")
                    var type = props.getString("type")
                    var time = props.getLong("time")
                    var magnitude = props.getDouble("mag")
                    var detailLink = props.getString("detail")
                    var dateFormat = DateFormat.getDateInstance()
                    var formatedDate = dateFormat.format(Date(props.getLong("time")).time)
                    earthQuake = EarthQuake(place, magnitude, time, detailLink, type, lat, lon)

                    var markerOptions = MarkerOptions()
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    markerOptions.title(earthQuake!!.place)
                    markerOptions.position(LatLng(lat, lon))
                    markerOptions.snippet("magnitude: \n ${earthQuake?.magnitude} Date: $formatedDate")

                    var marker = mMap.addMarker(markerOptions)
                    marker.tag = earthQuake?.detailLink
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 3f))

                }
            } catch (e: JSONException) {
                Log.d("VolleyEx", e.message)
            }

        }, Response.ErrorListener {

        })

        queue?.add(json)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setInfoWindowAdapter(CustomInfoWindow(this@MapsActivity))
        mMap.setOnInfoWindowClickListener(this@MapsActivity)
        mMap.setOnMarkerClickListener(this@MapsActivity)
        // Add a marker in Sydney and move the camera

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = listener
        if (ActivityCompat.checkSelfPermission(
                this@MapsActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this@MapsActivity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MapsActivity, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1111
            )
            return
        } else {
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(
                    this@MapsActivity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    locationListener
                )
            }
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    override fun onInfoWindowClick(marker: Marker?) {
        getEarthQuakeDetails(marker?.tag.toString())
    }

    private fun getEarthQuakeDetails(url: String) {
        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,
            Response.Listener {
                try {
                    var detailsUrl:String = ""
                    var props = it.getJSONObject("properties")
                    var geoserve = props.getJSONObject("products").getJSONArray("geoserve")

                    for (i in 0 until geoserve.length()){
                        var geoserveObj = geoserve.getJSONObject(i)
                        var jsonObject = geoserveObj.getJSONObject("contents")
                            .getJSONObject("geoserve.json")
                        detailsUrl = jsonObject.getString("url")
                    }

                    Log.d("DETAILSSSSS",detailsUrl)
                }catch (e:JSONException){
                    Log.d("DETAILSSSSS",e.message)
                }

            }, Response.ErrorListener {

            })

        queue?.add(jsonObjectRequest)
    }

    fun getMoreDetails(url: String){
        var json = JsonObjectRequest(Request.Method.GET,url,Response.Listener {

            dialogBuilder = AlertDialog.Builder(this@MapsActivity)
            var view = LayoutInflater.from(this@MapsActivity).inflate(R.layout.popup,null)
            dialogBuilder.setView(view)
            var dismissBtn = view.findViewById<Button>(R.id.dismissPop)
            var dismissBtnTop = view.findViewById<Button>(R.id.desmissPopTop)
            var popList = view.findViewById<TextView>(R.id.popList)
            var htmlPop = view.findViewById<WebView>(R.id.htmlWebview)
            
            dialog = dialogBuilder.create()
            dialog.show()
        }, Response.ErrorListener {

        })

        queue?.add(json)
    }
}
