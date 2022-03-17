package com.camelloncase.testedeperformance03.model

data class Recipe (
    var recipeName: String? = null,
    var recipeStyle: String? = null,
    var recipeCreateDate: String? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to recipeName,
            "style" to recipeStyle,
            "create_date" to recipeCreateDate
        )
    }
}