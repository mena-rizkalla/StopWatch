package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {
    lateinit var stopWatch : Chronometer
    var running = false
    var offset:Long =0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY= "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val startButton = findViewById<Button>(R.id.start)
        startButton.setOnClickListener {
            if (!running){
                setBaseTime()
                stopWatch.start();
                running =true
            }
        }

        val pauseButton = findViewById<Button>(R.id.pause)
        pauseButton.setOnClickListener {
            if (running){
                saveOffset()
                stopWatch.stop()
                running =false
            }
        }

        val resetButton = findViewById<Button>(R.id.reset)
        resetButton.setOnClickListener {
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