package com.example.sushilwedinginvitation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.sushilwedinginvitation.databinding.FragmentThirdBinding


class ThirdFragment(
   var activity: SecondScreen,
    var fm: FragmentManager?
) : Fragment() {
    private lateinit var binding: FragmentThirdBinding
    private var youtubePlayerFragment = YoutubePlayerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.supportFragmentManager.beginTransaction().add(R.id.youtubePlayer, YoutubePlayerFragment())
            .commit()
        //setFragment(youtubePlayerFragment)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }
    companion object {
    }

//    private fun setFragment(fragment: Fragment): Fragment {
//       fm!!.beginTransaction().replace(binding.youtube.id, YoutubePlayerFragment)
//            .commit()
//        return fragment
//    }
}