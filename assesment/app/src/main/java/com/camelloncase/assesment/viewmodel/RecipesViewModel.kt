package com.camelloncase.assesment.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.camelloncase.assesment.databinding.RecipeItemBinding
import com.camelloncase.assesment.model.Recipe
import com.camelloncase.assesment.repository.RecipeRepository
import com.camelloncase.assesment.util.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipesViewModel : ViewModel() {

    private var db = Firebase.firestore
    private val recipes = "Recipes"
    private val documentReference = db.collection(recipes)
    private val recipeRepository = RecipeRepository()

    private val _recipeLoadingStatus = MutableLiveData<Response<QuerySnapshot>>()
    val recipeLoadingStatus: LiveData<Response<QuerySnapshot>> = _recipeLoadingStatus

    val deleteLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getAllRecipes() {
        viewModelScope.launch(Dispatchers.IO) {

            val allRecipes = recipeRepository.getAllRecipes().data

            if (allRecipes == null) {
                _recipeLoadingStatus.postValue(Response.Failure("Empty list of recipes!"))
            } else {

                _recipeLoadingStatus.postValue(Response.Loading())
                _recipeLoadingStatus.postValue(Response.Success(allRecipes))

            }
        }
    }

    fun create(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepository.createRecipe(recipe)
        }
    }

    fun delete(id: String) {

//        viewModelScope.launch(Dispatchers.IO) {
//            recipeRepository.deleteRecipe(id)
//        }

        documentReference.document(id).delete().addOnSuccessListener {
            deleteLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("delete", it.localizedMessage!!)
            deleteLiveData.postValue(false)
        }
    }

}