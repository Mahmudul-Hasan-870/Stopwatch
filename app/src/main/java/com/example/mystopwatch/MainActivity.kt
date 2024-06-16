package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mystopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var handler: Handler = Handler()
    private var startTime: Long = 0L
    private var elapsedTime: Long = 0L
    private var isRunning: Boolean = false
    private var runnable: Runnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                val currentTime = System.currentTimeMillis()
                elapsedTime = currentTime - startTime
                updateTimer(elapsedTime)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            if (!isRunning) {
                startTime = System.currentTimeMillis() - elapsedTime
                handler.post(runnable)
                isRunning = true
            }
        }

        binding.btnStop.setOnClickListener {
            if (isRunning) {
                handler.removeCallbacks(runnable)
                isRunning = false
            }
        }

        binding.btnReset.setOnClickListener {
            handler.removeCallbacks(runnable)
            isRunning = false
            elapsedTime = 0L
            updateTimer(elapsedTime)
        }
    }

    private fun updateTimer(timeInMillis: Long) {
        val seconds = (timeInMillis / 1000) % 60
        val minutes = (timeInMillis / (1000 * 60)) % 60
        val hours = (timeInMillis / (1000 * 60 * 60)) % 24

        binding.tvTime.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
