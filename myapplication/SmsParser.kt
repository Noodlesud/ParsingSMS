package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class ParsedSMS(
    val date: String,
    val bankName: String,
    val time: String,
    val debitOrCredit: String,
    val amount: String,
    val remainingBalance: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(bankName)
        parcel.writeString(time)
        parcel.writeString(debitOrCredit)
        parcel.writeString(amount)
        parcel.writeString(remainingBalance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParsedSMS> {
        override fun createFromParcel(parcel: Parcel): ParsedSMS {
            return ParsedSMS(parcel)
        }

        override fun newArray(size: Int): Array<ParsedSMS?> {
            return arrayOfNulls(size)
        }
    }
}
