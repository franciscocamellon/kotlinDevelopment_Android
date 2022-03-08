package com.camelloncase.testedeperformance03.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("Select * from craft_beer_recipe_table order by recipeId ASC")
    fun getAllNotes(): LiveData<List<Recipe>>
}