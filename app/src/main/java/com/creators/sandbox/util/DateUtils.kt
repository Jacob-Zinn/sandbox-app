package com.creators.sandbox.util

import timber.log.Timber
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class DateUtils {

    companion object{

        fun returnTwelveWeekDateArray() : ArrayList<Long> {
            try {
                val oneWeek: Long = 604800
                val currentTime: Long = System.currentTimeMillis() / 1000

                val twelveWeekDateArray: ArrayList<Long> = ArrayList()
                for (i in 0..11) {
                    twelveWeekDateArray.add(currentTime - (i*oneWeek))
                }
                return twelveWeekDateArray
                // Returns miliseconds since 1970 for the past 12 weeks, starting with todays date

            } catch (e: java.lang.Exception) {
                throw java.lang.Exception(e)
            }
        }

        fun convertSecondsSinceEpochToDateString(epochSeconds: Int): String {
            try {
                val stamp = Timestamp(epochSeconds * 1000L)
                val unformattedDate = Date(stamp.time)
                val dateFormatter: DateFormat = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
                // Saturday, May 29

                return dateFormatter.format(unformattedDate)

            } catch (e: java.lang.Exception) {
                throw java.lang.Exception(e)
            }
        }

        fun convertSecondsSinceEpochToDateStringWithYear(epochSeconds: Int): String {
            try {
                val stamp = Timestamp(epochSeconds * 1000L)
                val unformattedDate = Date(stamp.time)
                val dateFormatter: DateFormat = SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault())
                // Saturday, Aug 1, 2020

                return dateFormatter.format(unformattedDate)

            } catch (e: java.lang.Exception) {
                throw java.lang.Exception(e)
            }
        }

        fun convertSecondsSinceEpochToDateStringLong(epochSecondsStart: Long, epochSecondsEnd: Long): String {
//            val epochSecondsEnd = epochSecondsEndDouble.toInt()

            try {
                val stampStart = Timestamp(epochSecondsStart * 1000L)
                val unformattedDateStart = Date(stampStart.time)
                val dateFormatterStart: DateFormat = SimpleDateFormat("EEEE, MMM d hh:mmaa", Locale.getDefault())
                // Saturday, Aug 1 12:10PM
                val initialDateTime: String = dateFormatterStart.format(unformattedDateStart)


                val stampEnd = Timestamp(epochSecondsEnd * 1000L)
                val unformattedDateEnd = Date(stampEnd.time)
                val dateFormatterEnd: DateFormat = SimpleDateFormat("hh:mmaa", Locale.getDefault())
                // 12:28PM
                val endDateTime: String = dateFormatterEnd.format(unformattedDateEnd)

                // Saturday, Aug 1 12:10PM - 12:28PM
                return "$initialDateTime - $endDateTime"

            } catch (e: java.lang.Exception) {
                throw java.lang.Exception(e)
            }
        }

        fun convertSecondsSinceEpochToDateStringShort(epochSecondsStart: Long, epochSecondsEnd: Long): String {

            try {
                val stamp = Timestamp(epochSecondsStart * 1000L)
                val stampEnd = Timestamp(epochSecondsEnd * 1000L)

                val unformattedDate = Date(stamp.time)
                val unformattedDateEnd = Date(stampEnd.time)

                val dateFormatter: DateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
                // Saturday, Aug 1 12:10PM
                val initialDateTime: String = dateFormatter.format(unformattedDate)
                val endDateTime: String = dateFormatter.format(unformattedDateEnd)

                // Mar 13 - Mar 20
                return "$initialDateTime - $endDateTime"

            } catch (e: java.lang.Exception) {
                throw java.lang.Exception(e)
            }
        }

        fun timestampToLongDateFormat(epochSeconds: Long): String {
            return try {
                val stamp = Timestamp(epochSeconds * 1000L)
                val unformattedDate = Date(stamp.time)
                val dateFormatter: DateFormat = SimpleDateFormat("MMM dd yyyy hh:mma", Locale.getDefault())
                // Jan 01 2021 12:00am
                dateFormatter.format(unformattedDate)
            } catch (e: Exception) {
                Timber.e(e, "timestampToLongDateFormat: failure to convert")
                "Jan 01 2021 12:00am"
            }

        }

        fun convertSecondsToLapTimeString(seconds: Float) : String {
            // 00:00:00
            return String.format("%02d:%02d.%02d", (seconds / 60.0).toInt(), (seconds % 60.0).toInt(), (((seconds % 60.0)*100.0) % 100.0).roundToInt())
        }

        fun convertSecondsToSessionLengthString(seconds: String) : String {
            // 5.9
            val secs = seconds.toDouble()
            return String.format("%.1f", secs/3600.0)
        }
        fun convertSecondsToMinAndSecString(seconds: Float): String {
            // 00:00s
            return String.format("%02d:%02d", (seconds / 60.0).toInt(), (seconds % 60.0).toInt())
        }

        // Used for sessions filter
        fun getStartOfDayInSeconds(startTime: Int): Int {
            return try {
                val calendar = Calendar.getInstance()
                calendar.time = Date(startTime.toLong() * 1000)
                calendar[Calendar.HOUR_OF_DAY] = 0
                calendar[Calendar.MINUTE] = 0
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
                (calendar.timeInMillis / 1000).toInt()
            } catch (e: Exception) {
                Timber.e(
                    e,
                    "getStartOfDayInSeconds: Failed to get start of day in seconds where startTime = $startTime"
                )
                startTime - 12 * 60 * 60  // returning filter that is + or - half a day in the case of a failure to parse correctly
            }
        }

        fun dateDiff(timestamp: Long): String {
            val ti: Long = System.currentTimeMillis() / 1000 - timestamp
            val diff: Long
            if(ti < 0.00001) {
                return "never"
            } else if (ti < 60) {
                return "just now"
            } else if (ti < 3600) {
                diff = (ti / 60.0).roundToLong()
                return "$diff mins ago"
            } else if (ti < 86400) {
                diff = (ti / 60.0 / 60.0).roundToLong()
                return "$diff hours ago"
            } else if (ti < (86400.0*2.0)) {
                //int diff = round(ti / 60 / 60 / 24)
                return "yesterday"
            } else if (ti < (2629743.0 * 1.6)) {
                diff = (ti / 60.0 / 60.0 / 24.0).roundToLong()
                return "$diff days ago"
            } else {
                diff = (ti / 60.0 / 60.0 / 24.0 / 30.0).roundToLong()
                return "$diff months ago"
            }
        }
    }
}