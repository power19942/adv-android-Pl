package com.example.pauloandroidcourse

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var locationRequest: LocationRequest? = null
    lateinit var client: GoogleApiClient
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var permissionsToRequest = ArrayList<String>()
    var permissions = ArrayList<String>()
    var permissionsRejected = ArrayList<String>()

    companion object {
        val UPDATE_INTERVAL: Long = 5000
        val FASTEST_INTERVAL: Long = 5000
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@MainActivity)

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)

        permissionsToRequest = permissionsToRequest(permissions)
        if (permissionsToRequest.size > 0){
            requestPermissions(permissionsToRequest.toTypedArray(),1111)
        }

        client = GoogleApiClient.Builder(this@MainActivity)
            .addApi(LocationServices.API)
            .addOnConnectionFailedListener(this@MainActivity)
            .addConnectionCallbacks(this@MainActivity)
            .build()
    }


    fun permissionsToRequest(permission: ArrayList<String>): ArrayList<String> {
        var results = ArrayList<String>()

        for (i in 0 until permission.size) {
            if (!hasPermission(permission[i])) {
                results.add(permission[i])
            }
        }
        return results
    }

    fun hasPermission(permission: String): Boolean {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }


    override fun onStart() {
        super.onStart()
        client?.connect()
    }

    override fun onStop() {
        super.onStop()
        client?.disconnect()
    }

    override fun onPause() {
        super.onPause()
        if (client?.isConnected) {
            LocationServices.getFusedLocationProviderClient(this@MainActivity)
                .removeLocationUpdates(object : LocationCallback() {})
            client?.disconnect()
        }
    }

    fun startLocationUpdate() {
        locationRequest = LocationRequest()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = UPDATE_INTERVAL
        locationRequest?.fastestInterval = FASTEST_INTERVAL
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this@MainActivity, "Permission denied for location", Toast.LENGTH_SHORT)
                .show()
        }

        LocationServices.getFusedLocationProviderClient(this@MainActivity)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    var location = locationResult?.lastLocation
                    locationTxt.text = "Updated ${location?.latitude},${location?.longitude}"
                }

                override fun onLocationAvailability(p0: LocationAvailability?) {
                    super.onLocationAvailability(p0)
                }
            }, null)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1111 -> {
                for (perm in permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm)
                    }
                }

                if (permissionsRejected.size > 0) {
                    if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                        AlertDialog.Builder(this@MainActivity)
                            .setMessage("These permissions are Mandatory to get location")
                            .setPositiveButton("Ok") { p0, p1 ->
                                requestPermissions(permissionsRejected.toTypedArray(),1111)
                            }
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show()
                    }
                } else {
                    client?.connect()
                }
            }
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        var errorCode = GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(this@MainActivity)
        if (errorCode != ConnectionResult.SUCCESS) {
            var errorDialog = GoogleApiAvailability.getInstance()
                .getErrorDialog(
                    this@MainActivity,
                    errorCode,
                    errorCode,
                    object : DialogInterface.OnCancelListener {
                        override fun onCancel(p0: DialogInterface?) {
                            Toast.makeText(
                                this@MainActivity,
                                "Location service Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            errorDialog.show()
        }
    }

    override fun onProviderDisabled(p0: String?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onLocationChanged(location: Location?) {
        locationTxt.text = "Location Changed ${location?.latitude},${location?.longitude}"
    }

    override fun onConnected(bundle: Bundle?) {
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
            return

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener {
                Log.d("Location321", it?.latitude.toString())
                locationTxt.text = it.latitude.toString() + "," + it.longitude
            }
        startLocationUpdate()
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

}
