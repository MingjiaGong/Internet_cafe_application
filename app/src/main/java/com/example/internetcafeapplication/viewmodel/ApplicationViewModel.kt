package com.example.internetcafeapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.internetcafeapplication.model.location.LocationLiveData

class ApplicationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData = LocationLiveData(application)
    fun getLocationLiveData() = locationLiveData
    fun startLocationUpdates() {
        locationLiveData.startLocationUpdates()
    }

}