package com.example.sushilwedinginvitation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(
    var activity: SecondScreen,
    var fm: FragmentManager?,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm!!, lifecycle) {
    private val tabCount = 3
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FirstFragment()
            1 -> fragment = SecondFragment()
            2 -> fragment = ThirdFragment(activity,fm)
        }
        return fragment!!
    }
    override fun getItemCount(): Int {
        return tabCount
    }
}
