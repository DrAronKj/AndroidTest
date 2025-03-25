package com.example.androidtest

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ListView
import android.widget.ArrayAdapter

class SuccessActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var textView: TextView
    private lateinit var deleteButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_success)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textView = findViewById(R.id.listView)
        deleteButton = findViewById(R.id.clearButton)
        dbHelper = DBHelper(this)

        // Prikazivanje popisa imena koji su napravili 10 koraka
        showUsers()

        // Brisanje popisa iz baze kad se klikne na gumb
        deleteButton.setOnClickListener {
            dbHelper.deleteAllUsers()
            showUsers()  // Osvježavanje popisa nakon brisanja
        }


        val phoneNumberGroup: RadioGroup = findViewById(R.id.phoneNumberGroup)
        val sendSMSButton: Button = findViewById(R.id.buttonSMS)

        // Set click listener for sending SMS
        sendSMSButton.setOnClickListener {
            // Get selected RadioButton's ID
            val selectedPhoneId = phoneNumberGroup.checkedRadioButtonId

            // Check if any RadioButton is selected
            if (selectedPhoneId == -1) {
                Toast.makeText(this, "Please select a phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get the selected RadioButton
            val selectedPhone: RadioButton = findViewById(selectedPhoneId)
            val phoneNumber = selectedPhone.text.toString()  // Get the phone number as string

            // Send SMS to the selected phone number
            sendSms(phoneNumber, "Your success message here!")
        }

        val userName = intent.getStringExtra("name")
        val success = getString(R.string.success, userName)

        val textViewMessage: TextView = findViewById(R.id.textView)
        textViewMessage.text = success


    }

    private fun showUsers() {
        // Dohvaćanje svih korisnika s 10 koraka
        val users = dbHelper.getUsersWith10Steps()
        val usersList = users.joinToString(",") { it }
        Log.d("SuccessActivity", "Dohvaćeni korisnici: $users")
        // Prikazivanje popisa u TextView
        textView.text = if (users.isNotEmpty()) usersList else "Nema korisnika s 10 koraka"
    }

    private fun sendSms(phoneNumber: String, message: String) {
        // Implicitni Intent za otvaranje SMS aplikacije
        //val phoneNumber = "123456789"   Zamijeniti s pravim brojem telefona
        val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phoneNumber")
            putExtra("sms_body", message)
        }
        startActivity(smsIntent)
    }
}