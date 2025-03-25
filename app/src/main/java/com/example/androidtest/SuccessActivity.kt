package com.example.androidtest

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

    private lateinit var dbHelper: StepDatabaseHelper
    private lateinit var listView: ListView
    private lateinit var clearButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dbHelper = StepDatabaseHelper(this)
        listView = findViewById(R.id.listView)
        clearButton = findViewById(R.id.clearButton)

        loadUsers()

        clearButton.setOnClickListener {
            dbHelper.clearUsers()
            loadUsers()
        }

        setContentView(R.layout.activity_success)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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

    private fun loadUsers() {
        val users = dbHelper.getUsersWith10Steps()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        listView.adapter = adapter
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