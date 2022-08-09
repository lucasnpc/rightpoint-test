package com.challenge.whatswrong.home

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.challenge.whatswrong.R
import com.challenge.whatswrong.core.launchWhenViewCreated
import com.challenge.whatswrong.core.viewBinding
import com.challenge.whatswrong.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val args by navArgs<HomeFragmentArgs>()

    init {
        launchWhenViewCreated {
            with(binding) {
                val headline = getString(R.string.welcome, args.firstName, args.lastName)
                accountDetailsHeadline.text = headline
            }
        }
    }
}
