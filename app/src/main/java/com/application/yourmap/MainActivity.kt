package com.application.yourmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class MainActivity : AppCompatActivity() {

    val key: String = "fe35bed6-6bef-401c-a925-5d12055f65a5"
    // val key: String = "c8767d61-788e-4ca7-bd1b-f75fc49f8373"

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(key)
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

    }


    override fun onResume() {
        super.onResume()
        openFragment(StartFragment())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.activity_main, fragment)
        transaction.commit()
    }

}