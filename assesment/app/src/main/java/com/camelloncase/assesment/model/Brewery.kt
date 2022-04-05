package com.camelloncase.assesment.model

import com.google.gson.annotations.SerializedName

data class Brewery(

    @SerializedName("id")
    val id: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("state")
    val state: String,

    @SerializedName("street")
    val street: String
)