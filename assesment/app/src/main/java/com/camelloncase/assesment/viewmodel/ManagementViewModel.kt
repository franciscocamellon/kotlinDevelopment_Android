package com.camelloncase.assesment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camelloncase.assesment.model.Brewery
import com.camelloncase.assesment.model.Recipe
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import retrofit2.Response

class ManagementViewModel : ViewModel() {

    private var db = Firebase.firestore
    private val recipes = "Recipes"
    private val documentReference = db.collection(recipes)



    private val _selectedRecipe = MutableLiveData<Recipe>()
    val selectedRecipe: LiveData<Recipe> = _selectedRecipe

    val createLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val updateLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun create(recipe: Recipe) {

        if (recipe.id != null) {
            documentReference.document(recipe.id!!).set(recipe.toMap())
                .addOnSuccessListener {
                    createLiveData.postValue(true)
                }.addOnFailureListener {
                    Log.d("create", it.localizedMessage!!)
                    createLiveData.postValue(false)
                }
        }
    }

    fun update(recipe: Recipe) {

        documentReference.document(recipe.id!!).update(recipe.toMap())
            .addOnSuccessListener {
                updateLiveData.postValue(true)
            }.addOnFailureListener {
                Log.d("update", it.localizedMessage!!)
                updateLiveData.postValue(false)
            }
    }

    fun getSelectedRecipe(recipe: Recipe) {

        _selectedRecipe.value = recipe
    }



}