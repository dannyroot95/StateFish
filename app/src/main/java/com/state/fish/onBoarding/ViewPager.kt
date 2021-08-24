package com.state.fish.onBoarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPager(fragment: FragmentManager, val behavior : Int) :  FragmentPagerAdapter(fragment){

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return FragmentA()
            1 -> return FragmentB()
            2 -> return FragmentC()

        }
        return FragmentA()
    }
}