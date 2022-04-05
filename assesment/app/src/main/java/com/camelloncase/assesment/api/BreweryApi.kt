package com.camelloncase.assesment.api

import com.camelloncase.assesment.model.Brewery
import retrofit2.Response
import retrofit2.http.GET

interface BreweryApi {

    @GET("breweries?by_city=new_york")
    suspend fun getBreweriesFromNewYork() : Response<List<Brewery>>
}