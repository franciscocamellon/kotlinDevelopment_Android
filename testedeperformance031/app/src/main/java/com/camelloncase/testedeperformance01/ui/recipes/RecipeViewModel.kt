package com.camelloncase.testedeperformance01.ui.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.camelloncase.testedeperformance01.model.Recipe
import com.camelloncase.testedeperformance01.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel (application: Application) : AndroidViewModel(application) {

    val allRecipes : LiveData<List<Recipe>>
    private val repository : RecipeRepository = RecipeRepository()

    init {
        allRecipes = repository.getListLiveData
    }

    fun deleteRecipe (recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        recipe.id?.let { repository.delete(it) }
    }

    fun updateRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(recipe)
    }

    fun addRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(recipe)
    }
}
