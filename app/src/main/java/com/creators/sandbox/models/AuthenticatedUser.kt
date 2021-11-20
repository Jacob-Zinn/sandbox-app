package com.creators.sandbox.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

const val ACCOUNT_PROPERTIES_BUNDLE_KEY = "com.example.litpro_mx.models.AuthenticatedUser"

@Parcelize
@Entity(tableName = "account_properties")
data class AuthenticatedUser(

    @SerializedName("identityId")
    @Expose
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk") var pk: Int = -1,

    @SerializedName("token")
    @Expose
    @ColumnInfo(name = "token") var token: String,

    @SerializedName("username")
    @Expose
    @ColumnInfo(name = "email") var email: String,

    @SerializedName("firstName")
    @Expose
    @ColumnInfo(name = "firstName") var firstName: String,

    @SerializedName("lastName")
    @Expose
    @ColumnInfo(name = "lastName") var lastName: String,

    @SerializedName("subLevel")
    @Expose
    @ColumnInfo(name = "subLevel") var subLevel: String,

    @SerializedName("error")
    @Expose
    @ColumnInfo(name = "error") var error: String?

) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as AuthenticatedUser

        if (pk != other.pk) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pk.hashCode()
        result = 31 * result + token.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + subLevel.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }

}











