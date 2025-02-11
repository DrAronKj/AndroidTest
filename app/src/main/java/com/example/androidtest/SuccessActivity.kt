package com.example.androidtest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SuccessActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_success)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userName = intent.getStringExtra("name")
        val success = "$userName uspješno ste došli do 10 koraka."

        val textViewMessage: TextView = findViewById(R.id.textView)
        textViewMessage.text = success

        // Postavljanje listenera na gumb za slanje SMS-a
        val sendSmsButton: Button = findViewById(R.id.buttonSMS)
        sendSmsButton.setOnClickListener {
            sendSms(success)
        }
    }

    private fun sendSms(message: String) {
        // Implicitni Intent za otvaranje SMS aplikacije
        val phoneNumber = "123456789"  // Zamijeniti s pravim brojem telefona
        val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phoneNumber")
            putExtra("sms_body", message)
        }
        startActivity(smsIntent)
    }
}