package com.example.weather.util

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.example.weather.ui.mSharePrefarence
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


fun String.fromHtml(): Spanned {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}



fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


fun Long.toDate(): String {
    val pattern = if (mSharePrefarence.getTimeFormat() == mSharePrefarence.TIME_24) {
        "MMM d. EEE, HH:mm"
    } else {
        "MMM d. EEE, hh:mm a"
    }
   return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this * 1000))
}


fun Long.toTime(): String {
    val pattern = if (mSharePrefarence.getTimeFormat() == mSharePrefarence.TIME_24) {
        "HH:mm"
    } else {
        "hh:mm a"
    }
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this * 1000))

}


fun Long.toDateTime(): String {
    return DateFormat.getDateTimeInstance().format(Date(this * 1000))
}

fun Double.toFahrenheit(): String = ((this * 9 / 5) + 32).roundToInt().toString()

fun Double.toKmHr(): String = String.format("%.2f", (this * 3.6))

fun Double.toMph(): String = String.format("%.2f", (this * 2.237))