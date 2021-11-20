package com.example.sandbox.util

object HashCustomStringToInt {
    fun hash(hashString: String): Int {
        var hash = 7
        try {
            for (i in hashString.indices) {
                hash = hash * 31 + hashString[i].code
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (hash < 0) return hash * -1
        return hash
    }
}