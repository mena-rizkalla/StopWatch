package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var  binding : ActivityMainBinding
    lateinit var stopWatch : Chronometer
    var running = false
    var offset:Long =0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY= "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stopWatch = findViewById(R.id.stopWatch)

        if (savedInstanceState!= null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)

            if (running){
                stopWatch.base = savedInstanceState.getLong(BASE_KEY)
                stopWatch.start()
            }else{
                setBaseTime()
            }
        }

        binding.start.setOnClickListener {
            if (!running){
                setBaseTime()
                stopWatch.start();
                running =true
            }
        }


        binding.pause.setOnClickListener {
            if (running){
                saveOffset()
                stopWatch.stop()
                running =false
            }
        }


        binding.reset.setOnClickListener {
            offset =0;
            setBaseTime()
        }

    }
    fun setBaseTime(){
        stopWatch.base = SystemClock.elapsedRealtime() - offset
    }

    fun saveOffset(){
        offset = SystemClock.elapsedRealtime() - stopWatch.base
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(OFFSET_KEY,offset)
        outState.putLong(BASE_KEY,stopWatch.base)
        outState.putBoolean(RUNNING_KEY,running)
        super.onSaveInstanceState(outState)
    }
}