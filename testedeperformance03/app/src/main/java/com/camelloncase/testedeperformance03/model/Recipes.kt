package com.camelloncase.testedeperformance03.model

class Recipes {

    var recipeName: String? = null
    var recipeStyle: String? = null
    var recipeCreateDate: String? = null

    constructor() {
        //empty constructor
    }

    constructor(recipeName: String?, recipeStyle: String?, recipeCreateDate: String?){
        this.recipeName = recipeName
        this.recipeStyle = recipeStyle
        this.recipeCreateDate = recipeCreateDate
    }
}