package com.example.androidtest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var counter = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var counterView: TextView

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @Suppress("DEPRECATION")
    fun changeLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        res.updateConfiguration(config, res.displayMetrics)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.restore_counter -> {
                counter = 0
                counterView.text = counter.toString()
                true
            }

        }
        when (item.itemId) {
            R.id.croatian -> {
                // Call changeLanguage function with Croatian language code "hr"
                changeLanguage(this, "hr")
                recreate()  // Refresh the activity to apply the language change
                return true
            }
            R.id.english -> {
                // Call changeLanguage function with English language code "en"
                changeLanguage(this, "en")
                recreate()  // Refresh the activity to apply the language change
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


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
        setSupportActionBar(findViewById(R.id.my_toolbar))

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

        var ButtonUp : Button
        var ButtonDown : Button

        counterView = findViewById(R.id.textViewCounter)
        ButtonUp = findViewById(R.id.buttonUp)
        ButtonDown = findViewById(R.id.buttonDown)

        counterView.text = counter.toString()

        ButtonUp.setOnClickListener {
            counter++
            if( counter == 10 ){
                counter = 0
                val intent = Intent( this, SuccessActivity::class.java).apply {
                    putExtra( "name", findViewById<TextView>(R.id.plainTextName).text.toString())
                }
                startActivity(intent)
            }
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


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_main, menu)
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
