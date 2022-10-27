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
        var result: String = ""
        var type: String = ""
        if (bundle != null) {
            result = bundle["RESULT"].toString()
            type = bundle["TYPE"].toString()
            binding.scannedResult.text = result
            binding.coinTypeTv.text = getString(R.string.coin_type, type)
        }

        binding.validateButton.setOnClickListener {
            if (checkAddress(result, type)){
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
    }

    private fun shareResult(address : String, type: String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "$type Address")
        intent.putExtra(Intent.EXTRA_TEXT, address)
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    private fun checkAddress(address: String, type: String) : Boolean{
        return true
    }
}