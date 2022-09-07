package com.application.yourmap.ui.fragments

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
import com.application.yourmap.R
import com.application.yourmap.databinding.FragmentLaunchBinding
import com.application.yourmap.functionality.getLatAndLong
import com.application.yourmap.functionality.isLocationPermissionGranted
import com.application.yourmap.functionality.locationPermissionCode

class LaunchFragment : BaseFragment() {

    companion object {
        const val LAUNCH = "launchFragment"
    }

    private lateinit var binding: FragmentLaunchBinding
    private var permissionDeniedJustNow = false
    private val argsCurrent = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.buttonLaunch.setOnClickListener {
            checkPermission()
            if (permissionDeniedJustNow) {
                replaceFragment(MapFragment(), LAUNCH)
                permissionDeniedJustNow = false
            }
        }

        val args = this.arguments
        if (args != null) {
            val addressValue = args.getString("keyForAddress")
            val latitudeValue = args.getDouble("keyForLat")
            val longitudeValue = args.getDouble("keyForLong")
            binding.textLaunch.text =
                addressValue
                    .plus(getString(R.string.latitude, latitudeValue.toString()))
                    .plus(getString(R.string.longitude, longitudeValue.toString()))
        } else {
            binding.textLaunch.text = getString(R.string.textLaunchEmpty)
        }
    }

    private fun checkPermission() {
        if (!isLocationPermissionGranted(context)) {
            activity?.let {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    locationPermissionCode
                )
            }
        } else {
            findLocation()
        }
    }

    private fun findLocation() {
        val latAndLong = getLatAndLong(context)
        val mapFragment = MapFragment()
        latAndLong.first?.let { argsCurrent.putDouble("keyForCurLat", it) }
        latAndLong.second?.let { argsCurrent.putDouble("keyForCurLon", it) }
        mapFragment.arguments = argsCurrent
        replaceFragment(mapFragment, LAUNCH)
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
                findLocation()
            } else {
                permissionDeniedJustNow = true
            }
        }
    }
}