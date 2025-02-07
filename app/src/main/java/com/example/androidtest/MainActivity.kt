package com.example.androidtest

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var counter = 0
    private lateinit var sharedPreferences: SharedPreferences

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt("counter", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter", 0)
        }

        sharedPreferences = getPreferences(MODE_PRIVATE)

        counter = sharedPreferences.getInt("counter", 0)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Toast.makeText(applicationContext, "onCreate", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onCreate")

        var counterView : TextView
        var ButtonUp : Button
        var ButtonDown : Button

        counterView = findViewById(R.id.textViewCounter)
        ButtonUp = findViewById(R.id.buttonUp)
        ButtonDown = findViewById(R.id.buttonDown)

        counterView.text = counter.toString()

        ButtonUp.setOnClickListener {
            counter++
            counterView.text = counter.toString()
        }

        ButtonDown.setOnClickListener {
            if( counter > 0) {
                counter--
                counterView.text = counter.toString()
            }
            else
                counterView.text = counter.toString()
        }
    }






    override fun onStart() {
        super.onStart()
        Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(applicationContext, "onResume", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(applicationContext, "onPause", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onPause")

        val editor = sharedPreferences.edit()
        editor.putInt("counter", counter)
        editor.apply()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(applicationContext, "onStop", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext, "onDestroy", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(applicationContext, "onRestart", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onRestart")
    }
}