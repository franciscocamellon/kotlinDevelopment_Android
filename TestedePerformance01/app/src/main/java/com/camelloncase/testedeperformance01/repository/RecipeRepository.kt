package com.camelloncase.testedeperformance01.repository

import androidx.lifecycle.LiveData
import com.camelloncase.testedeperformance01.database.Recipe
import com.camelloncase.testedeperformance01.database.RecipesDao

class RecipeRepository(private val recipesDao: RecipesDao) {

    val allNotes: LiveData<List<Recipe>> = recipesDao.getAllNotes()

    suspend fun insert(note: Recipe) {
        recipesDao.insert(note)
    }

    suspend fun update(note: Recipe){
        recipesDao.update(note)
    }

    suspend fun delete(note: Recipe){
        recipesDao.delete(note)
    }
}
