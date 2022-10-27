package com.example.cryptocheck

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cryptocheck.databinding.ActivityResultsBinding


class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_results)

        val bundle: Bundle? = intent.extras
        var result = ""
        var type = ""
        if (bundle != null) {
            result = bundle["RESULT"].toString() // scanned address from QR
            type = bundle["TYPE"].toString() // Type of currency based on button clicked on homepage
            binding.scannedResult.text = result
            binding.coinTypeTv.text = getString(R.string.coin_type, type)
        }

        binding.validateButton.setOnClickListener { // Checking validation of addresses
            if (checkAddress(result, type)) {
                binding.validatedResult.text = getString(R.string.valid_address, type)
            } else {
                binding.validatedResult.text = getString(R.string.invalid_address, type)
            }
        }

        binding.shareButton.setOnClickListener {
            if (checkAddress(result, type))
                shareResult(result, type)
            else
                Toast.makeText(this, "Invalid Address Cannot be shared", Toast.LENGTH_SHORT).show()
        }

        binding.rescanTv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun shareResult(address: String, type: String) { // Share result with other apps on device
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "$type Address")
        intent.putExtra(Intent.EXTRA_TEXT, "$type : $address")
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    private fun checkAddress(address: String, type: String): Boolean {
        return if (type == "BTC") {
            // Source : BTC & ETH Regex - https://gist.github.com/MBrassey/623f7b8d02766fa2d826bf9eca3fe005
            address.matches(Regex("^(bc1|[13])[a-zA-HJ-NP-Z0-9]{25,39}\$")) // BTC Regex
        } else {
            address.matches(Regex("^0x[a-fA-F0-9]{40}\$")) // ETH Regex
        }
    }
}