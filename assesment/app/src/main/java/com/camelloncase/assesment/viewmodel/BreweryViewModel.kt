package com.camelloncase.assesment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camelloncase.assesment.model.Brewery
import com.camelloncase.assesment.repository.BreweryRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class BreweryViewModel : ViewModel()  {

    private val repository = BreweryRepository()
    val allBreweriesByCity: MutableLiveData<Response<List<Brewery>>> = MutableLiveData()

    fun getAllNewYorkBreweries() {
        viewModelScope.launch {
            val response = repository.getAllNewYorkBreweries()
            allBreweriesByCity.value = response
        }
    }
}