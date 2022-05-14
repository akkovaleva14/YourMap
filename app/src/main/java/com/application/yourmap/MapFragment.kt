package com.application.yourmap


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.yourmap.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

/*   private MapView mapview;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            MapKitFactory.setApiKey("c8767d61-788e-4ca7-bd1b-f75fc49f8373");
            MapKitFactory.initialize(this);

            // Укажите имя activity вместо map.
            setContentView(R.layout.map);
            mapview = (MapView)findViewById(R.id.mapview);
            mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            new Animation(Animation.Type.SMOOTH, 0),
            null);
        } */


class MapFragment : BaseFragment() {

    lateinit var binding: FragmentMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

    companion object {

    }
}