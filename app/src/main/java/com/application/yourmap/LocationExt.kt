package com.application.yourmap

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle


@SuppressLint("MissingPermission")
// использовать только если разрешение УЖЕ получено
fun getLatAndLong(context: Context?): Pair<Double?, Double?> {
    var currentLocation: Location? = null
    var locationByGps: Location? = null
    var locationByNetwork: Location? = null
    var latitude: Double? = null
    var longitude: Double? = null
    val defaultLat = 55.7539
    val defaultLong = 37.6208

    val locationManager =
        context?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager

    val hasGps = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val hasNetwork = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    val gpsLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByGps = location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    val networkLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByNetwork = location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    if (hasGps == true) {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            500,
            1F,
            gpsLocationListener
        )
    }

    if (hasNetwork == true) {
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            500,
            1F,
            networkLocationListener
        )
    }

    val lastKnownLocationByGps =
        locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    lastKnownLocationByGps?.let {
        locationByGps = lastKnownLocationByGps
    }

    val lastKnownLocationByNetwork =
        locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    lastKnownLocationByNetwork?.let {
        locationByNetwork = lastKnownLocationByNetwork
    }

    if (locationByGps != null && locationByNetwork != null) {
        if (locationByGps?.accuracy ?: 0f > locationByNetwork?.accuracy ?: 0f) {
            currentLocation = locationByGps
            latitude = currentLocation?.latitude
            longitude = currentLocation?.longitude

        } else {
            currentLocation = locationByNetwork
            latitude = currentLocation?.latitude
            longitude = currentLocation?.longitude

        }
    } else if (locationByGps != null) {
        currentLocation = locationByGps
        latitude = currentLocation?.latitude
        longitude = currentLocation?.longitude

    } else if (locationByNetwork != null) {
        currentLocation = locationByNetwork
        latitude = currentLocation?.latitude
        longitude = currentLocation?.longitude

    } else {
        latitude = defaultLat
        longitude = defaultLong
    }

    locationManager?.removeUpdates(networkLocationListener)
    locationManager?.removeUpdates(gpsLocationListener)

    return Pair(latitude, longitude)
}