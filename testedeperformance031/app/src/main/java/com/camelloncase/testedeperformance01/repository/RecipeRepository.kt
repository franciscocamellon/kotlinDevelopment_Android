package com.camelloncase.testedeperformance01.repository

import androidx.lifecycle.MutableLiveData
import com.camelloncase.testedeperformance01.model.Recipe
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipeRepository() {

    private var db = Firebase.firestore
    private val recipes = "recipes"
    private val documentReference = db.collection(recipes)

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

    fun insert(recipe: Recipe) {

        documentReference.add(recipe.toMap()).addOnSuccessListener {
            createLiveData.postValue(true)
        }.addOnFailureListener {
            createLiveData.postValue(false)
//            showMessageToUser(this, "Failed to add data.")
        }

    }

    fun update(recipe: Recipe){

        documentReference.document(recipe.id!!).update(recipe.toMap())
            .addOnSuccessListener {
                updateLiveData.postValue(true)
            }.addOnFailureListener {
                updateLiveData.postValue(false)
        }
    }

    fun delete(id: String){
        documentReference.document(id).delete()
            .addOnSuccessListener {
                deleteLiveData.postValue(true)
            }.addOnFailureListener {
                deleteLiveData.postValue(false)
            }
    }

    fun getAllRecipes() {
        documentReference.get().addOnSuccessListener {
            val recipes = ArrayList<Recipe>()
            for (item in it.documents) {
                val recipe = Recipe()
                recipe.id = item.id
                recipe.recipeName = item.data!!["name"] as String?
                recipe.recipeStyle = item.data!!["style"] as String?
                recipe.recipeCreateDate = item.data!!["create_date"] as Timestamp?

                recipes.add(recipe)
            }
            getListLiveData.postValue(recipes)
        }.addOnFailureListener {
            getListLiveData.postValue(null)
        }
    }
}
