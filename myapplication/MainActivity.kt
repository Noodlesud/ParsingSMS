package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvDate: TextView
    private lateinit var tvBankName: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvDebitOrCredit: TextView
    private lateinit var tvAmount: TextView
    private lateinit var tvRemainingBalance: TextView

    private val smsViewModel: SmsViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val readGranted = permissions[Manifest.permission.READ_SMS] ?: false
        val receiveGranted = permissions[Manifest.permission.RECEIVE_SMS] ?: false
        if (!readGranted || !receiveGranted) {
            // Permission is denied. Show a message to the user.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDate = findViewById(R.id.tvDate)
        tvBankName = findViewById(R.id.tvBankName)
        tvTime = findViewById(R.id.tvTime)
        tvDebitOrCredit = findViewById(R.id.tvDebitOrCredit)
        tvAmount = findViewById(R.id.tvAmount)
        tvRemainingBalance = findViewById(R.id.tvRemainingBalance)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS
                )
            )
        }

        smsViewModel.parsedSMS.observe(this) { parsedSMS ->
            parsedSMS?.let {
                tvDate.text = "Date: ${it.date}"
                tvBankName.text = "Bank Name: ${it.bankName}"
                tvTime.text = "Time: ${it.time}"
                tvDebitOrCredit.text = "Debit or Credit: ${it.debitOrCredit}"
                tvAmount.text = "Amount: ${it.amount}"
                tvRemainingBalance.text = "Remaining Balance: ${it.remainingBalance}"
            }
        }
    }
}
