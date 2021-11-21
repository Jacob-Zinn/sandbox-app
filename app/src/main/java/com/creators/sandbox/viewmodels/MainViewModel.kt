package com.creators.sandbox.viewmodels


import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creators.sandbox.models.Doctor
import com.creators.sandbox.models.Queries
import com.creators.sandbox.repository.MainRepository
import com.creators.sandbox.session.SessionManager
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlin.math.exp


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    sessionManager: SessionManager
) : ViewModel() {

    // Jobs
    private var getDataJob: Job? = null

    // Live data
    val _list: MutableLiveData<List<Doctor>> = MutableLiveData()
    val list: LiveData<List<Doctor>>
        get() = _list
    val _viewingDoctor: MutableLiveData<Doctor> = MutableLiveData()
    val viewingDoctor: LiveData<Doctor>
        get() = _viewingDoctor

    val _expertiseList: MutableLiveData<List<String>> = MutableLiveData()
    val expertiseList: LiveData<List<String>>
        get() = _expertiseList
    val _qualitiesList: MutableLiveData<List<String>> = MutableLiveData()
    val qualitiesList: LiveData<List<String>>
        get() = _qualitiesList

    // Other vars
    var listLayoutManagerState: Parcelable? = null
    val rootRef = FirebaseDatabase.getInstance().reference
    val doctorRef = rootRef.child("doctors")

    val database = Firebase.database
    val myRef = database.getReference("doctors")

    val favoriteDoctorsList : MutableList<Doctor> = mutableListOf()
    var tmpDoctorList: MutableList<Doctor> = mutableListOf()


    //filters
    var queries: Queries = Queries()



    fun refreshData() {

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Timber.d("zzz Data changed")


                tmpDoctorList = parseData(snapshot).toMutableList()

                updateDoctorsCache(tmpDoctorList)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.d("zzz failed to read from db")
            }
        })

    }

    private fun parseData(dataSnapshot: DataSnapshot) : List<Doctor>{

        val doctors: MutableList<Doctor> = mutableListOf()

        for( child in dataSnapshot.children) {
            val jsonDoctor = child.value as HashMap<*,*>

            val doctor = Doctor(
                id  = (jsonDoctor["id"] as Long).toInt(),
                first_name = jsonDoctor["first_name"] as String,
                last_name = jsonDoctor["last_name"] as String,
                bedside_manners = (jsonDoctor["bedside_manners"] as Long).toInt(),
                years_experience = (jsonDoctor["years_experience"] as Long).toInt(),
                expertise_1 = jsonDoctor["expertise_1"] as String,
                expertise_2 = jsonDoctor["expertise_2"] as String,
                expertise_3 = jsonDoctor["expertise_3"] as String,
                location = jsonDoctor["location"] as String,
                distance = (jsonDoctor["id"] as Long).toInt(),
                doctor_qualities_1 = jsonDoctor["doctors_qualities_1"] as String,
                doctor_qualities_2 = jsonDoctor["doctors_qualities_2"] as String,
                doctor_qualities_3 = jsonDoctor["doctors_qualities_3"] as String,
                doctor_desciption_1 = jsonDoctor["doctor_description_1"] as String,
                doctor_desciption_2 = jsonDoctor["doctor_description_2"] as String,
                doctor_desciption_3 = jsonDoctor["doctor_description_3"] as String
            )

            doctors.add(doctor)

        }
        return doctors

    }

    private fun updateDoctorsCache(doctors: List<Doctor>) {
        viewModelScope.launch {
            mainRepository.updateCache(doctors)
        }
    }

    fun getFilteredDoctors() {
        if (getDataJob?.isActive != true) {
            getDataJob = viewModelScope.launch {

                val apiDataBundle = mainRepository.getFilteredDoctors(queries)

                MainScope().launch(Dispatchers.Main) {
                    apiDataBundle.data?.let { newData ->
                        setDoctors(newData)
                    }
                    apiDataBundle.errorResponse?.let {
                        Timber.e(Exception(), "Failed to filter. Message:  ${it.response.message}" )
                    }
                }
            }
        }
    }


    fun getFilteredDoctorsByDistance() {
        viewModelScope.launch {
            val apiDataBundle = mainRepository.getFilteredDoctorsByDistance(queries)

            MainScope().launch(Dispatchers.Main) {
                apiDataBundle.data?.let { newData ->
                    setDoctors(newData)
                }
                apiDataBundle.errorResponse?.let {
                    Timber.e(Exception(), "Failed to filter. Message:  ${it.response.message}")
                }
            }
        }
    }

    fun getFilteredDoctorsByCare() {
        viewModelScope.launch {
            val apiDataBundle = mainRepository.getFilteredDoctorsByCare(queries)

            MainScope().launch(Dispatchers.Main) {
                apiDataBundle.data?.let { newData ->
                    setDoctors(newData)
                }
                apiDataBundle.errorResponse?.let {
                    Timber.e(Exception(), "Failed to filter. Message:  ${it.response.message}")
                }
            }
        }
    }

    fun getFilteredDoctorsByExperience() {
        viewModelScope.launch {
            val apiDataBundle = mainRepository.getFilteredDoctorsByExperience(queries)

            MainScope().launch(Dispatchers.Main) {
                apiDataBundle.data?.let { newData ->
                    setDoctors(newData)
                }
                apiDataBundle.errorResponse?.let {
                    Timber.e(Exception(), "Failed to filter. Message:  ${it.response.message}")
                }
            }
        }
    }

    fun searchDoctorById(id: Int) {
        viewModelScope.launch {

            val apiDataBundle = mainRepository.searchDoctorById(id)

            MainScope().launch(Dispatchers.Main) {
                apiDataBundle.data?.let { newData ->
                    setViewingDoctor(newData)
                }
            }
        }
    }

    fun getExpertiseFilters() {
        viewModelScope.launch {

            val apiDataBundle = mainRepository.getDoctorExpertiseList()

            MainScope().launch(Dispatchers.Main) {
                apiDataBundle.data?.let { newData ->
                    setExpertiseFilterList(newData)
                }
            }
        }
    }

    fun getQualitiesFilters() {
        viewModelScope.launch {

            val apiDataBundle = mainRepository.getDoctorQualitiesList()

            MainScope().launch(Dispatchers.Main) {
                apiDataBundle.data?.let { newData ->
                    setQualitiesFilterList(newData)
                }
            }
        }
    }

    // SETTERS

    fun setDoctors(list: List<Doctor>) {
        _list.value = list
    }
    fun setViewingDoctor(doctor: Doctor) {
        _viewingDoctor.value = doctor
    }
    fun setQualitiesFilterList(list: List<String>) {
        _qualitiesList.value = list
    }
    fun setExpertiseFilterList(list: List<String>) {
        _expertiseList.value = list
    }

    fun setLayoutManagerState(layoutManagerState: Parcelable) {
        listLayoutManagerState = layoutManagerState
    }

    override fun onCleared() {
        super.onCleared()
        getDataJob?.cancel()
    }



}





































