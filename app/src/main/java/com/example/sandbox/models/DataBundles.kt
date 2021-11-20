package com.example.sandbox.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserSettings(
    var unitTypeIsImperial: Boolean = true,
    var hasLinkedPolarAccount: Boolean = false,
    var hasLinkedGarminAccount: Boolean = false,
) : Parcelable {
    override fun toString(): String {
        return "(unitTypeIsImperial: $unitTypeIsImperial, hasLinkedPolarAccount: $hasLinkedPolarAccount, hasLinkedGarminAccount: $hasLinkedGarminAccount)"
    }
}