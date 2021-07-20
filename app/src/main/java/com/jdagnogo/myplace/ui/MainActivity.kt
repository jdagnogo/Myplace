package com.jdagnogo.myplace.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.jdagnogo.myplace.R
import com.jdagnogo.myplace.ui.fragment.BaseFragment
import com.jdagnogo.myplace.ui.fragment.HomeFragment
import com.jdagnogo.myplace.ui.fragment.VenueDetailsFragment
import dagger.android.AndroidInjection

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onNavigationToFragment(tag: String): Boolean {
        val fragment: BaseFragment = when (tag) {
            HomeFragment.TAG -> {
                supportFragmentManager.findFragmentByTag(tag)
                    ?: HomeFragment.newInstance()
            }
            else -> {
                supportFragmentManager.findFragmentByTag(tag)
                    ?: VenueDetailsFragment.newInstance()
            }
        } as BaseFragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment, fragment.getFragmentTag())
            addToBackStack(null)
            commit()
        }
        return true
    }
}