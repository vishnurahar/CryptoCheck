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
    private lateinit var binding : ActivityMainBinding
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

    private fun openScanner(coinType : String) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            /* open QR code scanner */
            val intent = Intent(this, ScannerActivity::class.java)
            intent.putExtra("TYPE", coinType)
            startActivity(intent)
        }else {
            askForPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
        }
    }

    private fun askForPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
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