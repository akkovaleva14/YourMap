package com.application.yourmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction

abstract class BaseFragment : Fragment() {

    fun replaceFragment(fragment: BaseFragment, fragmentTag: String?) {
        var transaction: FragmentTransaction = childFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
        transaction = transaction.addToBackStack(fragmentTag)
        transaction.commitAllowingStateLoss()
    }

   /* companion object {

    }*/
}