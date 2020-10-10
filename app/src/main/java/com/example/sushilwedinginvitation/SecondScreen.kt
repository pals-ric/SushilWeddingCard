package com.example.sushilwedinginvitation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.sushilwedinginvitation.databinding.ActivitySecondBinding
import com.google.android.material.tabs.TabLayoutMediator

class SecondScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        binding.viewpager.adapter = MyAdapter(this,supportFragmentManager, lifecycle)

//            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        TabLayoutMediator(binding.tab, binding.viewpager, TabLayoutMediator.TabConfigurationStrategy{tab, position ->
            when (position) {
                0 -> tab.text = "Family"
                1 -> tab.text = "Functions"
                2 -> tab.text = "Glimpses"
            }
        }).attach()

    }





}