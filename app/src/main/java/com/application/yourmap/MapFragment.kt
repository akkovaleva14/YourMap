package com.application.yourmap


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.yourmap.GeocoderImpl.Companion.getAddressByLatAndLong
import com.application.yourmap.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.ui_view.ViewProvider


class MapFragment : BaseFragment() {

    lateinit var binding: FragmentMapBinding
    val args = Bundle()

    companion object {
        const val MAP = "mapFragment"
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argsCur = this.arguments
        if (argsCur != null) {
            val curLat = argsCur.getDouble("keyForCurLat", 55.7539)
            val curLon = argsCur.getDouble("keyForCurLon", 37.6208)
            setCameraToPosition(curLat, curLon)
        }
        getPoint()
    }

    override fun onResume() {
        super.onResume()
        binding.buttonPoint.setOnClickListener {
            val inputsFragment = InputsFragment()
            inputsFragment.arguments = args
            replaceFragment(inputsFragment, MAP)
        }
    }

    private fun setCameraToPosition(curLat: Double, curLon: Double) {
        binding.mapview.map.move(
            CameraPosition(
                Point(curLat, curLon),
                16.0f, 0.0f, 0.0f
            ),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
    }

    val listener = object : InputListener {
        @SuppressLint("UseCompatLoadingForDrawables")
        override fun onMapTap(map: Map, point: Point) {
            val view = View(context).apply {
                background = context.getDrawable(R.drawable.ic_baseline_place_24)
            }
            val newPoint = Point(point.latitude, point.longitude)
            binding.mapview.map.mapObjects.clear()
            addMarker(newPoint.latitude, newPoint.longitude, view)
            binding.viewInfo.visibility = View.VISIBLE
            val address =
                context?.let { getAddressByLatAndLong(it, newPoint.latitude, newPoint.longitude) }
            binding.textPoint.text = address
            args.putString("keyForAddress", address)
            args.putDouble("keyForLat", newPoint.latitude)
            args.putDouble("keyForLong", newPoint.longitude)
        }

        override fun onMapLongTap(p0: Map, p1: Point) {
        }

    }


    private fun addMarker(latitude: Double, longitude: Double, view: View): PlacemarkMapObject {
        return binding.mapview.map.mapObjects.addPlacemark(
            Point(latitude, longitude),
            ViewProvider(view)
        )
    }

    private fun getPoint() {
        binding.mapview.map.addInputListener(listener)
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}