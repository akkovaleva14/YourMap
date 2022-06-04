package com.application.yourmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.yourmap.databinding.FragmentInputsBinding


class InputsFragment : BaseFragment() {
    lateinit var binding: FragmentInputsBinding

    companion object {
        const val INPUT = "inputsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val args = this.arguments
        if (args != null) {
            val addressValue = args.getString("keyForAddress")
            val latitudeValue = args.getDouble("keyForLat")
            val longitudeValue = args.getDouble("keyForLong")

            binding.textAddress.text = addressValue
            binding.textLat.text = getString(R.string.latitude, latitudeValue.toString())
            binding.textLong.text = getString(R.string.longitude, longitudeValue.toString())
        }
    }
}