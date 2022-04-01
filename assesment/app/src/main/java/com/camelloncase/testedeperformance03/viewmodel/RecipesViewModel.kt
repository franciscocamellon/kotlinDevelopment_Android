package com.camelloncase.testedeperformance03.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.camelloncase.testedeperformance03.model.Recipe
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipesViewModel : ViewModel() {

    private var db = Firebase.firestore
    private val recipes = "Recipes"
    private val documentReference = db.collection(recipes)

    val getListLiveData: MutableLiveData<List<Recipe>> by lazy {
        MutableLiveData<List<Recipe>>()
    }

    val deleteLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getList() {

        documentReference.get().addOnSuccessListener {
            val recipes = ArrayList<Recipe>()
            for (item in it.documents) {
                val recipe = Recipe()
                recipe.id = item.id
                recipe.recipeName = item.data!!["name"] as String?
                recipe.recipeStyle = item.data!!["style"] as String?
                recipe.recipeCreateDate = item.data!!["create_date"] as String?
                recipe.recipeUpdateDate = item.data!!["update_date"] as String?
                recipes.add(recipe)
            }

            getListLiveData.postValue(recipes)
        }.addOnFailureListener {
            Log.d("get", it.localizedMessage!!)
            getListLiveData.postValue(null)
        }
    }

    fun delete(id: String) {

        documentReference.document(id).delete().addOnSuccessListener {
            deleteLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("delete", it.localizedMessage!!)
            deleteLiveData.postValue(false)
        }
    }

}