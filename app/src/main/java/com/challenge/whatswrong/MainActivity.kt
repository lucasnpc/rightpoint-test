package com.challenge.whatswrong

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.challenge.whatswrong.core.viewBinding
import com.challenge.whatswrong.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
