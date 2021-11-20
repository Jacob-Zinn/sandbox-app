package com.creators.sandbox.models

import com.creators.sandbox.util.ErrorHandling.Companion.ERROR_SAVE_ACCOUNT_PROPERTIES
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "doctor")
data class Doctor(

    @Expose
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Int = ERROR_SAVE_ACCOUNT_PROPERTIES,

    @Expose
    @ColumnInfo(name = "first_name") var first_name: String,

    @Expose
    @ColumnInfo(name = "last_name") var last_name: String,

    @Expose
    @ColumnInfo(name = "bedside_manners") var bedside_manners: Int,

    @Expose
    @ColumnInfo(name = "years_experience") var years_experience: Int,

    @Expose
    @ColumnInfo(name = "types_of_doctors") var types_of_doctors: Int,

    @Expose
    @ColumnInfo(name = "expertise_1") var expertise_1: String,

    @Expose
    @ColumnInfo(name = "expertise_2") var expertise_2: String,

    @Expose
    @ColumnInfo(name = "expertise_3") var expertise_3: String,

    @Expose
    @ColumnInfo(name = "location") var location: String,

    @Expose
    @ColumnInfo(name = "distance") var distance: Int,

    @Expose
    @ColumnInfo(name = "doctor_qualities_1") var doctor_qualities_1: String,

    @Expose
    @ColumnInfo(name = "doctor_qualities_2") var doctor_qualities_2: String,

    @Expose
    @ColumnInfo(name = "doctor_qualities_3") var doctor_qualities_3: String,

    @Expose
    @ColumnInfo(name = "doctor_desciption_1") var doctor_desciption_1: String,

    @Expose
    @ColumnInfo(name = "doctor_desciption_2") var doctor_desciption_2: String,

    @Expose
    @ColumnInfo(name = "doctor_desciption_3") var doctor_desciption_3: String

)










