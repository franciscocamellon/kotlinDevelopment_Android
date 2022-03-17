package com.camelloncase.testedeperformance03.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.camelloncase.testedeperformance03.model.Recipe
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipeViewModel: ViewModel() {
    private var db = Firebase.firestore
    private val recipes = "Recipes"

    val createLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val updateLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val getListLiveData: MutableLiveData<List<Recipe>> by lazy {
        MutableLiveData<List<Recipe>>()
    }

    val deleteLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun create(recipe: Recipe) {
        val docRef = db.collection(recipes)
        docRef.add(recipe.toMap()).addOnSuccessListener {
            createLiveData.postValue(true)

        }.addOnFailureListener {
            Log.d("create", it.localizedMessage!!)
            createLiveData.postValue(false)
        }
    }

    fun update(recipe: Recipe) {
        val docRef = db.collection(recipes)
        docRef.document(recipe.recipeName!!).update(recipe.toMap()).addOnSuccessListener {
            updateLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("update", it.localizedMessage!!)
            updateLiveData.postValue(false)
        }
    }

    fun delete(id: String) {
        val docRef = db.collection(recipes)
        docRef.document(id).delete().addOnSuccessListener {
            deleteLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("delete", it.localizedMessage!!)
            deleteLiveData.postValue(false)
        }
    }

    fun getList() {
        val docRef = db.collection(recipes)
        docRef.get().addOnSuccessListener {
            val recipes = ArrayList<Recipe>()
            for (item in it.documents) {
                val recipe = Recipe()
                recipe.recipeName = item.data!!["name"] as String?
                recipe.recipeStyle = item.data!!["style"] as String?
                recipe.recipeCreateDate = item.data!!["create_date"] as String?
                recipes.add(recipe)
            }

            getListLiveData.postValue(recipes)
        }.addOnFailureListener {
            Log.d("get", it.localizedMessage!!)
            getListLiveData.postValue(null)
        }
    }
}