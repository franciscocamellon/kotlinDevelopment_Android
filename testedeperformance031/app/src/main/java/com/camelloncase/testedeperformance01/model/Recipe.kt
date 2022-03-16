package com.camelloncase.testedeperformance01.model

import com.google.firebase.Timestamp


data class Recipe (

    var id: String? = null,
    var recipeName: String? = null,
    var recipeStyle: String? = null,
    var recipeCreateDate: Timestamp? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to recipeName,
            "style" to recipeStyle,
            "create_date" to recipeCreateDate
        )
    }


}
