package com.wan.ricestockpredictor

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wan.ricestockpredictor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var predictionHelper: PredictionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        predictionHelper = PredictionHelper(
            context = this,
            onResult = { result -> binding.tvResult.text = result },
            onError = { errorMessage ->
                Toast.makeText(
                    this@MainActivity,
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            },
            onDownloadSuccess = {}
        )
        binding.btnPredict.setOnClickListener {
            val input = binding.edSales.text.toString()
            predictionHelper.predict(input)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        predictionHelper.close()
    }
}