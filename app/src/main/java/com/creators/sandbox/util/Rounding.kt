package com.creators.sandbox.util

import timber.log.Timber
import java.math.RoundingMode
import java.text.DecimalFormat

class Rounding {

    companion object{

        fun roundToOneDecimalReturnFloat(number: Float): Float {
            return try {
                val df = DecimalFormat("#.#")
                df.roundingMode = RoundingMode.HALF_UP
                df.format(number).toFloat()
            } catch (e: Exception) {
                Timber.e(e, "roundToOneDecimal: ERROR")
                number
            }
        }

        fun roundToTwoDecimalReturnFloat(number: Float): Float {
            return try {
                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.HALF_UP
                df.format(number).toFloat()
            } catch (e: Exception) {
                Timber.e(e, "roundToTwoDecimal: ERROR")
                number
            }
        }

        fun roundToOneDecimalReturnString(number: Float): String {
            return try {
                val df = DecimalFormat("#.#")
                df.roundingMode = RoundingMode.HALF_UP
                df.format(number)
            } catch (e: Exception) {
                Timber.e(e, "roundToOneDecimalString: ERROR")
                number.toString().replaceAfter('.',"",number.toString())
            }
        }

        fun roundToTwoDecimalReturnString(number: Float): String {
            return try {
                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.HALF_UP
                df.format(number)
            } catch (e: Exception) {
                Timber.e(e, "roundToTwoDecimalString: ERROR")
                number.toString().replaceAfter('.',"",number.toString())
            }
        }

        fun roundToWholePercentageReturnString (number: Float): String {
            return try {
                val df = DecimalFormat("##%")
                df.roundingMode = RoundingMode.HALF_UP
                df.format(number)
            } catch (e: Exception) {
                Timber.e(e, "roundTwoTwoDigit Whole Percentgage: ERROR")
                "${number.toString().take(2)}%"
            }
        }

    }
}