package com.creators.sandbox.repository


import android.content.SharedPreferences
import com.creators.sandbox.api.ApiMainService
import com.creators.sandbox.models.Doctor
import com.creators.sandbox.models.Queries
import com.creators.sandbox.persistence.DoctorsDao
import com.creators.sandbox.session.SessionManager
import com.creators.sandbox.util.Constants
import com.creators.sandbox.util.DataBundle
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener

import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.nznlabs.litpromx.repository.getCacheResponse


class MainRepository @Inject constructor(
    val apiMainService: ApiMainService,
    val doctorsDao: DoctorsDao,
    val sessionManager: SessionManager,
    val sharedPreferences: SharedPreferences,
    private val sharedPrefsEditor: SharedPreferences.Editor
) {

    suspend fun updateCache(doctors: List<Doctor>) : DataBundle<Boolean> {
        val cacheResponse = getCacheResponse(
            cacheCall = { doctorsDao.insertList(doctors) },
            dispatcher = Dispatchers.IO
        )

        if (cacheResponse.errorResponse == null) {
            return DataBundle.data(true)
        } else {
            return DataBundle.data(false)
        }
    }

    suspend fun getDoctorQualitiesList() : DataBundle<List<String>> {
        return getCacheResponse(
            cacheCall = { doctorsDao.getDoctorQualitiesSet() },
            dispatcher = Dispatchers.IO
        )
    }


    suspend fun getDoctorExpertiseList() : DataBundle<List<String>> {
        return getCacheResponse(
            cacheCall = { doctorsDao.getDoctorExpertiseSet() },
            dispatcher = Dispatchers.IO
        )
    }

    suspend fun getDoctorsByCare() : DataBundle<List<Doctor>> {
        return getCacheResponse(
            cacheCall = { doctorsDao.doctorsByCare() },
            dispatcher = Dispatchers.IO
        )
    }

    suspend fun getDoctorsByDistance() : DataBundle<List<Doctor>> {
        return getCacheResponse(
            cacheCall = { doctorsDao.doctorsByDistance() },
            dispatcher = Dispatchers.IO
        )
    }

    suspend fun getDoctorsByExperience() : DataBundle<List<Doctor>> {
        return getCacheResponse(
            cacheCall = { doctorsDao.doctorsByExperience() },
            dispatcher = Dispatchers.IO
        )
    }

    suspend fun getFilteredDoctors(
        queries: Queries
    ) : DataBundle<List<Doctor>> {
        return getCacheResponse(
            cacheCall = { doctorsDao.getFilteredDoctors(
                quality1 = queries.quality1 ?: Constants.WILDCARD_OPERATOR,
                quality2 = queries.quality2 ?: Constants.WILDCARD_OPERATOR,
                quality3 = queries.quality3 ?: Constants.WILDCARD_OPERATOR,
                expertise1 = queries.expertise1 ?: Constants.WILDCARD_OPERATOR,
                expertise2 = queries.expertise2 ?: Constants.WILDCARD_OPERATOR,
                expertise3 = queries.expertise3 ?: Constants.WILDCARD_OPERATOR,
            ) },
            dispatcher = Dispatchers.IO
        )
    }

    suspend fun getFilteredDoctorsByDistance(
        queries: Queries
    ) : DataBundle<List<Doctor>> {
        return getCacheResponse(
            cacheCall = { doctorsDao.filteredDoctorsByDistance(
                quality1 = queries.quality1 ?: Constants.WILDCARD_OPERATOR,
                quality2 = queries.quality2 ?: Constants.WILDCARD_OPERATOR,
                quality3 = queries.quality3 ?: Constants.WILDCARD_OPERATOR,
                expertise1 = queries.expertise1 ?: Constants.WILDCARD_OPERATOR,
                expertise2 = queries.expertise2 ?: Constants.WILDCARD_OPERATOR,
                expertise3 = queries.expertise3 ?: Constants.WILDCARD_OPERATOR,
            ) },
            dispatcher = Dispatchers.IO
        )
    }

    suspend fun getFilteredDoctorsByCare(
        queries: Queries
    ) : DataBundle<List<Doctor>> {
        return getCacheResponse(
            cacheCall = { doctorsDao.filteredDoctorsByCare(
                quality1 = queries.quality1 ?: Constants.WILDCARD_OPERATOR,
                quality2 = queries.quality2 ?: Constants.WILDCARD_OPERATOR,
                quality3 = queries.quality3 ?: Constants.WILDCARD_OPERATOR,
                expertise1 = queries.expertise1 ?: Constants.WILDCARD_OPERATOR,
                expertise2 = queries.expertise2 ?: Constants.WILDCARD_OPERATOR,
                expertise3 = queries.expertise3 ?: Constants.WILDCARD_OPERATOR,
            ) },
            dispatcher = Dispatchers.IO
        )
    }

    suspend fun getFilteredDoctorsByExperience(
        queries: Queries
    ) : DataBundle<List<Doctor>> {
        return getCacheResponse(
            cacheCall = { doctorsDao.filteredDoctorsByExperience(
                quality1 = queries.quality1 ?: Constants.WILDCARD_OPERATOR,
                quality2 = queries.quality2 ?: Constants.WILDCARD_OPERATOR,
                quality3 = queries.quality3 ?: Constants.WILDCARD_OPERATOR,
                expertise1 = queries.expertise1 ?: Constants.WILDCARD_OPERATOR,
                expertise2 = queries.expertise2 ?: Constants.WILDCARD_OPERATOR,
                expertise3 = queries.expertise3 ?: Constants.WILDCARD_OPERATOR,
            ) },
            dispatcher = Dispatchers.IO
        )
    }


    suspend fun searchDoctorById(id: Int) : DataBundle<Doctor> {
        return getCacheResponse(
            cacheCall = { doctorsDao.seachDoctorById(id) },
            dispatcher = Dispatchers.IO
        )
    }




























    suspend fun parseData(dataSnapshot: DataSnapshot) {
//        val doctors: MutableList<Doctor> = mutableListOf()
//
//        for( child in dataSnapshot.children) {
//            doctors.add( Gson().fromJson(child, Doctor::class.java))
//        }
//        val doctor = Gson().fromJson(dataSnapshot, Doctor::class.java)

    }




//
//    override suspend fun getDataFromCache(): DataBundle<List<Athlete>> {
//        return getCacheResponse(
//            cacheCall = { athletesDao.getAthletes() },
//            dispatcher = Dispatchers.IO
//        )
//    }
//
//    override suspend fun getDataFromApi(
//        email: String
//    ): DataBundle<List<Doctor>> {
//
//        val athletesApiResponse: DataBundle<List<Doctor>> = getApiResponse(
//            apiCall = { apiMainService.getLinkedAthletes(email) },
//            dispatcher = Dispatchers.IO
//        )
//
//        // Getting seatTime & most active rider data to add proStatus parameter to Athlete
//        val activityMetricsApiResponse: DataBundle<ActivityMetricsResponse> = getApiResponse(
//            apiCall = {
//                apiMainService.getActivityMetrics(
//                    email = email,
//                    includeCrashes = "yes",
//                    app = Constants.APP_NAME,
//                    appVersion = BuildConfig.VERSION_NAME
//                )
//            },
//            dispatcher = Dispatchers.IO
//        )
//        // Parsing activity metrics if value successfully returned
//        val athleteActivityMap = hashMapOf<String, AthleteActivityMetrics>()
//        activityMetricsApiResponse.data?.let {
//            val jsonObject: JsonObject = it.results
//            var hasProStatusRing: Boolean
//            var hasProStatusThumb: Boolean
//            try {
//                for (key in jsonObject.keySet()) {
//                    val userObject: JsonObject = jsonObject[key].asJsonObject
//                    val timeTwoWeeksAgoInSeconds =
//                        System.currentTimeMillis() / 1000 - 1209600
//                    val lastSession = userObject["lastSessionTS"].asLong
//                    hasProStatusRing = lastSession > timeTwoWeeksAgoInSeconds
//                    val totalSeatTime = userObject["12WeekSeatTime"].asInt
//                    hasProStatusThumb = totalSeatTime > 36000
//                    athleteActivityMap[key] = AthleteActivityMetrics(
//                        hasProStatusRing,
//                        hasProStatusThumb,
//                        lastSession
//                    )
//                }
//            } catch (e: Exception) {
//                Timber.e(e, "MainFeedRepo: ERROR parsing json object")
//            }
//        }
//
//        athletesApiResponse.data?.let { responseData ->
//            val athletesList = responseData.toList(athleteActivityMap)
//
//            // updating cache
//            withContext(Dispatchers.IO) {
//                for (athlete in athletesList) {
//                    try {
//                        // Launch each insert as a separate job to be executed in parallel
//                        launch {
//                            athletesDao.insert(athlete)
//                        }
//                    } catch (e: Exception) {
//                        Timber.e(
//                            e, "updateLocalDb: error updating cache data on Feed " +
//                                    "instance with firsLastName: ${athlete.firstLastName}. " +
//                                    "${e.message}"
//                        )
//                    }
//                }
//            }
//        }
//
//        // return recently updated cache
//        return getLinkedAthletesFromCache()
//    }
}




