package com.application.yourmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {

    val key: String = "fe35bed6-6bef-401c-a925-5d12055f65a5"

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(key)
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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