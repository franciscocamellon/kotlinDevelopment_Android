package com.camelloncase.assesment.repository

import com.camelloncase.assesment.api.RetrofitInstance
import com.camelloncase.assesment.model.Brewery
import retrofit2.Response

class BreweryRepository {

    suspend fun getAllNewYorkBreweries(): Response<List<Brewery>> {
        return RetrofitInstance.api.getBreweriesFromNewYork()
    }
}