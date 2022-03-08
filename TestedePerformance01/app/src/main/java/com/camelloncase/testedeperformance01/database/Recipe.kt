package com.camelloncase.testedeperformance01.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "craft_beer_recipe_table")
class Recipe (

    @ColumnInfo(name = "recipe_name")
    val recipeName: String,

    @ColumnInfo(name = "recipe_style")
    val recipeStyle: String,

    @ColumnInfo(name = "recipe_create_date")
    val recipeCreateDate: String) {

    @PrimaryKey(autoGenerate = true) var recipeId = 0
}
