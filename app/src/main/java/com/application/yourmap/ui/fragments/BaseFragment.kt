package com.application.yourmap.ui.fragments

import androidx.fragment.app.Fragment
import com.application.yourmap.R

abstract class BaseFragment : Fragment() {

    fun replaceFragment(fragment: BaseFragment, fragmentTag: String?) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.activity_main, fragment, fragmentTag)
            ?.addToBackStack(fragmentTag)
            ?.commit()
    }
}