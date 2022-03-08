package com.camelloncase.testedeperformance01.ui.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.camelloncase.testedeperformance01.database.Recipe
import com.camelloncase.testedeperformance01.database.RecipesDatabase
import com.camelloncase.testedeperformance01.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel (application: Application) : AndroidViewModel(application) {

    val allNotes : LiveData<List<Recipe>>
    private val repository : RecipeRepository

    init {
        val dao = RecipesDatabase.getDatabase(application).getNotesDao()
        repository = RecipeRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteRecipe (recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(recipe)
    }

    fun updateRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(recipe)
    }

    fun addRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(recipe)
    }
}
