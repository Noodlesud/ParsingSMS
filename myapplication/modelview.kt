package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SmsViewModel : ViewModel() {
    private val _parsedSMS = MutableLiveData<ParsedSMS?>()
    val parsedSMS: LiveData<ParsedSMS?> get() = _parsedSMS

    fun updateParsedSMS(parsedSMS: ParsedSMS?) {
        _parsedSMS.postValue(parsedSMS)
    }
}
