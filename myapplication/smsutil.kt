package com.example.myapplication

fun parseSMS(sms: String): ParsedSMS? {
    val pattern = """account '.*' is (\w+) with ETB ([\d,]+.\d{2}) on (\d{2}/\d{2}/\d{4}) at (\d{2}:\d{2}).*balance is ETB ([\d,]+.\d{2}).""".toRegex()
    val matchResult = pattern.find(sms)

    return if (matchResult != null) {
        val (debitOrCredit, amount, date, time, remainingBalance) = matchResult.destructured
        ParsedSMS(date, "Commercial Bank of Ethiopia", time, debitOrCredit, amount, remainingBalance)
    } else {
        null
    }
}
