package com.example.sandbox.util

class ImperialMetricConversions {

    companion object{

        fun feetToMiOneDecimal(feet: Float) : String {
            // 0.2
            val miles = feet/5280
            return Rounding.roundToOneDecimalReturnString(miles)
        }

        fun feetToMeterOneDecimal(feet: Float) : String {
            // 0.2
            val meters = feet * 0.3048000097536f
            return Rounding.roundToOneDecimalReturnString(meters)
        }

        fun feetToKmOneDecimal(feet: Float) : String {
            // 0.2
            val miles = feet/3280.839895f
            return Rounding.roundToOneDecimalReturnString(miles)
        }

        fun mphToKphOneDecimal(mphMetric: Float): String{
            // 8.95
            val kph = mphMetric*1.60934f
            return Rounding.roundToOneDecimalReturnString(kph)
        }


    }
}