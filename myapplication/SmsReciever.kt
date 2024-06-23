package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            val bundle: Bundle? = intent?.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<Any>?
                if (pdus != null) {
                    for (pdu in pdus) {
                        val smsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            SmsMessage.createFromPdu(pdu as ByteArray, bundle.getString("format"))
                        } else {
                            SmsMessage.createFromPdu(pdu as ByteArray)
                        }
                        val sender = smsMessage.displayOriginatingAddress
                        val messageBody = smsMessage.messageBody

                        // Check if the sender is "CBE"
                        if (sender == "CBE") {
                            val parsedSMS = parseSMS(messageBody)

                            parsedSMS?.let {
                                // Update the ViewModel with the parsed SMS
                                val viewModelProvider = ViewModelProvider(context as ViewModelStoreOwner)
                                val smsViewModel = viewModelProvider.get(SmsViewModel::class.java)
                                smsViewModel.updateParsedSMS(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
