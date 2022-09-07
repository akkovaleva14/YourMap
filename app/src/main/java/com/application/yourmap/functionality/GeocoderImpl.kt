package com.application.yourmap.functionality

import android.content.Context
import android.location.Geocoder
import java.util.*

class GeocoderImpl {
    companion object {
        fun getAddressByLatAndLong(context: Context, latitude: Double, longitude: Double): String {
            try {

                val geocoder = Geocoder(
                    context,
                    Locale.Builder().setLanguage("RU").setScript("Latn").setRegion("RS").build()
                )
                val geocoderLocation = geocoder.getFromLocation(latitude, longitude, 1)

                if (!geocoderLocation.isNullOrEmpty()) {
                    return geocoderLocation[0]?.getAddressLine(0).toString()
                }

                return ""
            } catch (e: Exception) {
                return ""
            }
        }
    }
}