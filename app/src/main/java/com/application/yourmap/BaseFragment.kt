package com.application.yourmap

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    fun replaceFragment(fragment: BaseFragment, fragmentTag: String?) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.activity_main, fragment, fragmentTag)
            ?.addToBackStack(fragmentTag)
            ?.commit()
    }

}