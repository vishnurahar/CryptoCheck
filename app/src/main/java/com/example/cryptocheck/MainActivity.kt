package com.example.cryptocheck

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.cryptocheck.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btcTv.setOnClickListener {
            openScanner("BTC")
        }

        binding.ethTv.setOnClickListener {
            openScanner("ETH")
        }
    }

    // RunTime Permission reference - https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/
    private fun openScanner(coinType: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            // opening scanner activity
            val intent = Intent(this, ScannerActivity::class.java)
            intent.putExtra("TYPE", coinType)
            startActivity(intent)
        } else {
            // ask for permission if not given already
            askForPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
        }
    }

    private fun askForPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }
}