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

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


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
    val myRef = database.getReference("core-depth-332615-default-rtdb")

    val favoriteDoctorsList : MutableList<Doctor> = mutableListOf()
    var tmpDoctorList: MutableList<Doctor> = mutableListOf()


    //filters
    var queries: Queries = Queries()



    fun refreshData() {


        // Read from the database
//        rootRef.addValueEventListener(object: ValueEventListener() {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                for (dataSnapshot in snapshot.children) {
//                    val doctor: Doctor? = dataSnapshot.getValue(Doctor::class.java)
//                    if (doctor != null) {
//                        tmpDoctorList.add(doctor)
//                        Timber.d("logging doctor: $doctor")
//                    }
//                }
//                val value = snapshot.getValue<String>()
//                Log.d(TAG, "Value is: " + value)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//
//        })

        Timber.d("zzz Making it here")

        doctorRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Timber.d("Data change executedzzz")
                for (dataSnapshot in snapshot.children) {
                    val doctor: Doctor? = dataSnapshot.getValue(Doctor::class.java)
                    if (doctor != null) {
                        tmpDoctorList.add(doctor)
                    }
                }
                Timber.d("zzz doctors $tmpDoctorList")

                updateDoctorsCache(tmpDoctorList)
                tmpDoctorList.clear()
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.d("zzz failed to read from db")
            }
        })






//
//
//
//
//
//        doctorRef.get()
//            .addOnCompleteListener(OnCompleteListener<DataSnapshot?> { task ->
//                if (!task.isSuccessful) {
//                    Timber.e(task.exception,"firebase", "Error getting data")
//                } else {
//                    val result = task.result?.value
//                    Timber.d("result from firebase : $result")
////                    updateDoctorsCache(task.result?.value as List<Doctor>)
////                    Timber.d("firebase", java.lang.String.valueOf(task.result.getValue()))
//                }
//            })

//        val doctors: List<Doctor> = rootRef.child("core-depth-332615-default-rtdb").get().result?.value as List<Doctor>
//        updateDoctorsCache(doctors)



//        mDatabase.child("users").child(userId).get().addOnCompleteListener(
//            OnCompleteListener<DataSnapshot?> { task ->
//                if (!task.isSuccessful) {
//                    Log.e("firebase", "Error getting data", task.exception)
//                } else {
//                    Log.d("firebase", java.lang.String.valueOf(task.result.getValue()))
//                }
//            })

    }

    private fun parseData() {

//        if (getDataJob?.isActive != true) {
//            getDataJob = viewModelScope.launch {
//
//                val apiDataBundle = mainRepository.getDataFromApi()
//
//                MainScope().launch(Dispatchers.Main) {
//                    apiDataBundle.data?.let { newData ->
//                        setNewData(newData)
//                    }
//                }
//            }
//        }
    }

    private fun updateDoctorsCache(doctors: List<Doctor>) {
        if (getDataJob?.isActive != true) {
            getDataJob = viewModelScope.launch {

                val apiDataBundle = mainRepository.updateCache(doctors)

            }
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





































