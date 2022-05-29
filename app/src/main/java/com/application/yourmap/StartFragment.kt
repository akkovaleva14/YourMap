package com.application.yourmap

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.application.yourmap.databinding.FragmentStartBinding

class StartFragment : BaseFragment() {

    companion object {
        const val START = "startFragment"
    }

    private val locationPermissionCode = 109
    private var permissionDeniedJustNow = false
    lateinit var binding: FragmentStartBinding
    private val argsCurrent = Bundle()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.buttonStart.setOnClickListener {
            checkPermission()
        }
        if (permissionDeniedJustNow) {
            replaceFragment(MapFragment(), START)
            permissionDeniedJustNow = false
        }
    }


    private fun checkPermission() {
        if (!isLocationPermissionGranted()) {
            activity?.let {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    locationPermissionCode
                )
                Toast.makeText(context, "It doesn't work YET", Toast.LENGTH_LONG).show()
            }
        } else {
            findLocation()
            Toast.makeText(context, "It works", Toast.LENGTH_LONG).show()
        }
    }


    private fun isLocationPermissionGranted(): Boolean {
        return context?.let { context ->
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        } ?: false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 1) {
            if (requestCode == locationPermissionCode &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                checkGps()
                Toast.makeText(context, "It works RIGHT NOW", Toast.LENGTH_LONG).show()
            } else {
                permissionDeniedJustNow = true
                Toast.makeText(context, "It doesn't work", Toast.LENGTH_LONG).show()
            }
        }
    }

    /* получаем у нашей системы - location-сервис. мы его получили: в том случае, если он доступен,
    мы переходим на нашу findLocation()
    [ проверяем, включен ли gps ] */
    private fun checkGps() {

        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            findLocation()
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

        }
    }

    fun findLocation() {
        val latAndLong = getLatAndLong(context)
        val mapFragment = MapFragment()
        latAndLong.first?.let { argsCurrent.putDouble("keyForCurLat", it) }
        latAndLong.second?.let { argsCurrent.putDouble("keyForCurLon", it) }
        mapFragment.arguments = argsCurrent
        replaceFragment(mapFragment, START)
    }
}