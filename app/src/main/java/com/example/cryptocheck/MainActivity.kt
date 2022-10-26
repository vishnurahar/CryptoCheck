package com.example.cryptocheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptocheck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.btcTv.setOnClickListener {
            // open QR code scanner
        }

        binding.ethTv.setOnClickListener {
            // open QR code scanner
        }
    }
}