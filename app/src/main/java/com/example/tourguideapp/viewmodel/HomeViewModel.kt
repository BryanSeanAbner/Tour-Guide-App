package com.example.tourguideapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tourguideapp.data.DummyData
import com.example.tourguideapp.model.Tour

class HomeViewModel : ViewModel() {
    private val _tours = MutableLiveData<List<Tour>>()
    val tours: LiveData<List<Tour>> = _tours

    init {
        loadTours()
    }

    private fun loadTours() {
        _tours.value = DummyData.tours
    }
}