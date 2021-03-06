package com.camelloncase.assesment.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Recipe (

    var id: String? = null,
    var recipeName: String? = null,
    var recipeStyle: String? = null,
    var recipeCreateDate: String? = null,
    var recipeUpdateDate: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to recipeName,
            "style" to recipeStyle,
            "create_date" to recipeCreateDate,
            "update_date" to recipeUpdateDate
        )
    }
}