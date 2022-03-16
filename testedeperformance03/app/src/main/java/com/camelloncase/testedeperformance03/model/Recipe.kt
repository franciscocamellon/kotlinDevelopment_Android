package com.camelloncase.testedeperformance03.model

import com.google.firebase.Timestamp

data class Recipe (
    var id: String? = null,
    var recipeName: String? = null,
    var recipeStyle: String? = null,
    var recipeCreateDate: Timestamp?  = null,
    var recipeUpdateDate: Timestamp?  = null,
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "recipeName" to recipeName,
            "recipeStyle" to recipeStyle,
            "recipeCreateDate" to recipeCreateDate,
            "recipeUpdateDate" to recipeUpdateDate
        )
    }
}