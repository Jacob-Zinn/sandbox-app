package com.example.sandbox.viewmodels


import android.content.SharedPreferences
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sandbox.models.ListItem
import com.example.sandbox.models.User
import com.example.sandbox.repository.MainRepository
import com.example.sandbox.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPrefsEditor: SharedPreferences.Editor,
    sessionManager: SessionManager
    ) : ViewModel() {

    // Jobs
    private var getDataJob: Job? = null

    // Live data
    val _newData: MutableLiveData<String> = MutableLiveData()
    val newData: LiveData<String>
        get() = _newData
    val _list: MutableLiveData<List<ListItem>> = MutableLiveData()
    val list: LiveData<List<ListItem>>
        get() = _list

    // Other vars
    var user: User = User(pk = 1, email = "N/A", firstName = "N/A", lastName =  "N/A", null)
    var listLayoutManagerState: Parcelable? = null


    fun refreshData() {
        getNewData()
    }

    private fun getNewData() {
        if (getDataJob?.isActive != true) {
            getDataJob = viewModelScope.launch {
                val apiDataBundle = mainRepository.getData()

                MainScope().launch(Dispatchers.Main) {
                    apiDataBundle.data?.let { newData ->
                        setNewData(newData)
                    }
                }
            }
        }
    }

    // SETTERS
    fun setNewData(newData: String) {
        _newData.value = newData
    }
    fun setList(list: List<ListItem>) {
        _list.value = list
    }
    fun setLayoutManagerState(layoutManagerState: Parcelable) {
        listLayoutManagerState = layoutManagerState
    }

    override fun onCleared() {
        super.onCleared()
        getDataJob?.cancel()
    }


}





































