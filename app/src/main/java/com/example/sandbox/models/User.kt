package com.example.sandbox.models

import com.example.sandbox.util.ErrorHandling.Companion.ERROR_SAVE_ACCOUNT_PROPERTIES
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "user")
data class User(

    @SerializedName("identityId")
    @Expose
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk") var pk: Int = ERROR_SAVE_ACCOUNT_PROPERTIES,

    @ColumnInfo(name = "email") var email: String,

    @SerializedName("firstName")
    @Expose
    @ColumnInfo(name = "firstName") var firstName: String,

    @SerializedName("lastName")
    @Expose
    @ColumnInfo(name = "lastName") var lastName: String,

    @SerializedName("error")
    @Expose
    @ColumnInfo(name = "error") var error: String?
)  {

    override fun hashCode(): Int {
        var result = pk.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (pk != other.pk) return false
        if (email != other.email) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (error != other.error) return false

        return true
    }

}











