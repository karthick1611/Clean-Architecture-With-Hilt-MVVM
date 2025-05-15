package com.clean_architecture.hilt_mvvm.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.clean_architecture.hilt_mvvm.R
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class Methods {

    fun showNonCancelAlertMessage(
        context: Context?,
        positive: String?,
        negative: String?,
        message: String?,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?
    ) {
        val builder: AlertDialog.Builder
        builder = AlertDialog.Builder(context!!)
        builder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(positive, positiveListener)
            .setNegativeButton(negative, negativeListener)
            .create()
        val alertDialog = builder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
    }

    companion object {
        const val IS_CHECK_MINE = 2

        const val IS_CHARGER_REMOTE_START = 0
        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS

        val currentTimeINDesiredFormat: String
            get() {
                val sdf = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss.SSS",
                    Locale.US
                )
                return sdf.format(Date())
            }

        fun getTimeAgoWithoutString(date: String, ctx: Context): String {
            var date = date
            return try {
                val utcTimeZone = TimeZone.getTimeZone("UTC")
                //          Log.e("DateMethods",date);
                date = date.replace("T", " ").replace("Z", "")
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
                sdf.timeZone = utcTimeZone
                val dateUtc = sdf.parse(date)
                val timeZoneDefault = TimeZone.getDefault()
                val sdfLocal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
                sdfLocal.timeZone = timeZoneDefault
                val dateLocalString = sdfLocal.format(dateUtc)
                val date1 = sdfLocal.parse(dateLocalString)
                val time = date1.time
                //          Log.e("DateReplaced", date1+"");
//          if (time < 1000000000000L) {
//              // if timestamp given in seconds, convert to millis
//              time *= 1000;
//          }
                val now = System.currentTimeMillis()
                //          Log.e("StartMethodsTime", time+"");
//          Log.e("NowMethodsTime", now+"");
                if (time > now || time <= 0) {
                    //return ctx.getString(R.string.app_charging_started);
                    return "0" + ctx.getString(R.string.str_min)
                }

                val diff = now - time
                if (diff < 1 * MINUTE_MILLIS) {
                    "0" + ctx.getString(R.string.str_min)
                } else if (diff < 2 * MINUTE_MILLIS) {
                    "1" + ctx.getString(R.string.str_min)
                } else if (diff < 60 * MINUTE_MILLIS) {
                    (diff / MINUTE_MILLIS).toString() + "" + ctx.getString(R.string.str_min)
                } else {
                    var hrdetails = ""
                    if ((diff / (1000 * 60 * 60)).toInt() > 1) {
                        hrdetails = (diff / (1000 * 60 * 60)).toInt()
                            .toString() + "h"
                    } else {
                        hrdetails = (diff / (1000 * 60 * 60)).toInt()
                            .toString() + "h"
                    }
                    var mindetails = ""
                    mindetails = if ((diff / (1000 * 60) % 60).toInt() > 1) {
                        if ((diff / (1000 * 60) % 60).toInt() <= 9) {
                            (diff / (1000 * 60) % 60).toInt()
                                .toString() + ctx.getString(R.string.str_min)
                        } else {
                            (diff / (1000 * 60) % 60).toInt()
                                .toString() + ctx.getString(R.string.str_min)
                        }
                    } else {
                        (diff / (1000 * 60) % 60).toInt()
                            .toString() + ctx.getString(R.string.str_min)
                    }
                    "$hrdetails $mindetails"
                    /*if( (int) (diff / (1000 * 60 * 60)) >1 ) {
                    return (int) (diff / (1000 * 60 * 60)) + "" + ctx.getString(R.string.str_hrs) + " " + (int) ((diff / (1000 * 60)) % 60) + ctx.getString(R.string.str_mins);
                }else{
                    return (int) (diff / (1000 * 60 * 60)) + "" + ctx.getString(R.string.str_hr) + " " + (int) ((diff / (1000 * 60)) % 60) + ctx.getString(R.string.str_mins);

                }*/
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        fun showAlertMessage(
            context: Context?,
            OK: String?,
            message: String?,
            okListener: DialogInterface.OnClickListener?,
            dismissListener: DialogInterface.OnDismissListener?
        ) {
            val builder: AlertDialog.Builder
            builder = AlertDialog.Builder(context!!)
            builder.setMessage(message)
                .setPositiveButton(OK, okListener)
                .setOnDismissListener(dismissListener)
            val alertDialog = builder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
        }

        fun call(context: Context, number: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$number")
            context.startActivity(intent)
        }

        fun mailToWithData(context: Context, mailId: String, subject: String?, body: String?) {
            val mailto =
                "mailto:" + mailId + "?" +  //                "?cc=" + "alice@example.com" +
                        //                "&subject=" + Uri.encode(subject) +
                        "subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(body)
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse(mailto)
            try {
                context.startActivity(emailIntent)
            } catch (e: ActivityNotFoundException) {

                Toast.makeText(
                    context,
                    "context.getString(R.string.app_you_need_to_configure_email)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun isRightToLeft(context: Context): Boolean {
            var isRightToLeft = false
            val config = context.resources.configuration
            if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                //in Right To Left layout
                isRightToLeft = true
            }
            return isRightToLeft
        }

        fun setStatusBarColor(context: Context, window: Window) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(context, R.color.closeColor)
            window.navigationBarColor = ContextCompat.getColor(context, R.color.white)
        }

        private fun checkDateDifference(startDate: Long, endDate: Long): Long {
            //milliseconds
            var different = startDate - endDate
            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val elapsedDays = different / daysInMilli
            different %= daysInMilli
            val elapsedHours = different / hoursInMilli
            different %= hoursInMilli
            val elapsedMinutes = different / minutesInMilli
            different %= minutesInMilli
            val elapsedSeconds = different / secondsInMilli
            return Math.abs(elapsedDays)
        }

        fun getTimeInformat1(datetime: Calendar): String {
            val myFormat = "HH:mm"
            val simpleDateFormat = SimpleDateFormat(myFormat, Locale.US)
            return simpleDateFormat.format(datetime.time)
        }

        fun isExpire(date: String): Boolean {
            if (date.isEmpty() || (date.trim { it <= ' ' } == "")) {
                return false
            } else {
                val sdf = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.US
                ) // Jan-20-2015 1:30:55 PM
                val d: Date
                val d1: Date
                val today = today
                try {
                    d = sdf.parse(date)
                    d1 = sdf.parse(today)
                    if (d1.compareTo(d) < 0) { // not expired
                        return false
                    } else if (d.compareTo(d1) == 0) { // both date are same
                        if (d.time < d1.time) { // not expired
                            return false
                        } else return if (d.time == d1.time) { //expired
                            true
                        } else { //expired
                            true
                        }
                    } else { //expired
                        return true
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                    return false
                }
            }
        }

        private val today: String
            get() {
                val format = "dd/MM/yyyy HH:mm"
                val date = Date()
                return SimpleDateFormat(format, Locale.US).format(date)
            }
        val todayCurrent: String
            get() {
                val format = "dd/MMM/yyyy HH:mm:ss"
                val date = Date()
                return SimpleDateFormat(format, Locale.US).format(date)
            }

        fun String.digitsOnly(): String {
            val regex = Regex("[^0-9]")
            return regex.replace(this, "")
        }


        fun getScreenWidth(context: Context): Int {
            val displayMetrics = context.resources.displayMetrics
            return displayMetrics.widthPixels
        }

        fun getValueInDP(context: Context, value: Int): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }

        fun setRFIDText(rfid: String, isRtl: Boolean): String {
            return when {
                rfid.length > 4 -> if (isRtl) "${rfid.takeLast(4)}..." else "...${rfid.takeLast(4)}"
                else -> rfid
            }
        }

        fun setLanguage(lang: String, context: Context, activity: Activity) {
            try {
                Log.e("Locale", Locale.getDefault().displayLanguage)
                val conf = activity.baseContext.resources.configuration
                val locale = Locale(lang)
                Locale.setDefault(locale)

                conf.setLocale(locale)

                if (shouldEnableRTL(lang)) {
                    conf.setLayoutDirection(locale)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    context.createConfigurationContext(conf)
                    activity.applicationContext.createConfigurationContext(conf)
                } else {
                    activity.baseContext.resources.updateConfiguration(
                        conf,
                        activity.baseContext.resources.displayMetrics
                    )
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(lang)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun shouldEnableRTL(lang: String): Boolean {
            // Determine which languages should have RTL support
            return lang == "he" || lang == "iw"
        }

        fun isValidIdentificationNumber(creditCardNumber: String?): Boolean {
            // Reverse the credit card number
            val reversedCreditCardNumber = creditCardNumber?.reversed()

            var total = 0

            // Double every other digit and add the digits together if the result is a two-digit number
            if (reversedCreditCardNumber != null) {
                for (i in reversedCreditCardNumber.indices) {
                    var digit = Integer.parseInt(reversedCreditCardNumber[i].toString())
                    if ((i + 1) % 2 == 0) {
                        digit *= 2
                        if (digit > 9) {
                            digit -= 9
                        }
                    }
                    total += digit
                }
            }

            // Check if the total is divisible by 10
            return total % 10 == 0
        }
    }

}