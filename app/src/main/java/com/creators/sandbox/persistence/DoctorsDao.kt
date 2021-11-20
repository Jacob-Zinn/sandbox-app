package com.creators.sandbox.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.creators.sandbox.models.AuthenticatedUser
import com.creators.sandbox.models.Doctor

@Dao
interface DoctorsDao {


    @Query("""
        SELECT * FROM doctor 
        WHERE (
        doctor_qualities_1 LIKE (:quality1) 
        OR doctor_qualities_2 LIKE (:quality2) 
        OR doctor_qualities_2 LIKE (:quality3)
        ) AND (
        expertise_1 LIKE (:expertise1)
        OR expertise_2 LIKE (:expertise2)
        OR expertise_3 LIKE (:expertise3)
        )
        """)
    suspend fun getFilteredDoctors(
        quality1: String,
        quality2: String,
        quality3: String,
        expertise1: String,
        expertise2: String,
        expertise3: String
    ): List<Doctor>?

    @Query(" SELECT DISTINCT doctor_qualities_1 FROM doctor ")
    suspend fun getDoctorQualitiesSet(): List<String>

    @Query(" SELECT DISTINCT expertise_1 FROM doctor ")
    suspend fun getDoctorExpertiseSet(): List<String>



    @Query("""
        SELECT * FROM doctor 
        WHERE (
        doctor_qualities_1 LIKE (:quality1) 
        OR doctor_qualities_2 LIKE (:quality2) 
        OR doctor_qualities_2 LIKE (:quality3)
        ) AND (
        expertise_1 LIKE (:expertise1)
        OR expertise_2 LIKE (:expertise2)
        OR expertise_3 LIKE (:expertise3)
        )
        ORDER BY distance ASC
        """)
    suspend fun filteredDoctorsByDistance(
        quality1: String,
        quality2: String,
        quality3: String,
        expertise1: String,
        expertise2: String,
        expertise3: String
    ): List<Doctor>?

    @Query("""
        SELECT * FROM doctor 
        WHERE (
        doctor_qualities_1 LIKE (:quality1) 
        OR doctor_qualities_2 LIKE (:quality2) 
        OR doctor_qualities_2 LIKE (:quality3)
        ) AND (
        expertise_1 LIKE (:expertise1)
        OR expertise_2 LIKE (:expertise2)
        OR expertise_3 LIKE (:expertise3)
        )
        ORDER BY bedside_manners DESC
        """)
    suspend fun filteredDoctorsByCare(
        quality1: String,
        quality2: String,
        quality3: String,
        expertise1: String,
        expertise2: String,
        expertise3: String
    ): List<Doctor>?

    @Query("""
        SELECT * FROM doctor 
        WHERE (
        doctor_qualities_1 LIKE (:quality1) 
        OR doctor_qualities_2 LIKE (:quality2) 
        OR doctor_qualities_2 LIKE (:quality3)
        ) AND (
        expertise_1 LIKE (:expertise1)
        OR expertise_2 LIKE (:expertise2)
        OR expertise_3 LIKE (:expertise3)
        )
        ORDER BY years_experience DESC
        """)
    suspend fun filteredDoctorsByExperience(
        quality1: String,
        quality2: String,
        quality3: String,
        expertise1: String,
        expertise2: String,
        expertise3: String
    ): List<Doctor>?


    @Query("SELECT * FROM Doctor ORDER BY distance ASC")
    suspend fun doctorsByDistance(): List<Doctor>

    @Query("SELECT * FROM Doctor ORDER BY years_experience DESC")
    suspend fun doctorsByExperience(): List<Doctor>

    @Query("SELECT * FROM Doctor ORDER BY bedside_manners DESC")
    suspend fun doctorsByCare(): List<Doctor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(doctorsList: List<Doctor>): List<Long>


    @Query("SELECT * FROM doctor WHERE id LIKE :id")
    suspend fun seachDoctorById(id: Int) : Doctor

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAndReplace(doctor: Doctor): Long
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertOrIgnore(doctor: Doctor): Long

}









//    @Query("SELECT * FROM doctor WHERE doctor_qualities_1 in (:qualities) OR expertise_1 in (:expertise) ORDER BY distance ASC")
//    suspend fun filteredDoctorsByDistance(
//        qualities: List<String>,
//        expertise: List<String>
//    ): List<Doctor>
//
//    @Query("SELECT * FROM doctor WHERE doctor_qualities_1 in (:qualities) OR expertise_1 in (:expertise) ORDER BY years_experience DESC")
//    suspend fun filteredDoctorsByExperience(
//        qualities: List<String>,
//        expertise: List<String>
//    ): List<Doctor>
//
//    @Query("SELECT * FROM doctor WHERE doctor_qualities_1 in (:qualities) OR expertise_1 in (:expertise) ORDER BY bedside_manners DESC")
//    suspend fun filteredDoctorsByCare(
//        qualities: List<String>,
//        expertise: List<String>
//    ): List<Doctor>







