package com.camelloncase.assesment.repository

import android.util.Log
import com.camelloncase.assesment.model.Recipe
import com.camelloncase.assesment.util.Response
import com.camelloncase.assesment.util.responseSafeCall
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RecipeRepository {

    private var db = Firebase.firestore
    private val recipes = "Recipes"
    private val documentReference = db.collection(recipes)

    suspend fun getAllRecipes(): Response<QuerySnapshot> {

        return withContext(Dispatchers.IO) {
            responseSafeCall {

                val snapshot = documentReference.get().await()

                if (snapshot == null) {
                    Response.Failure("Failed on get firebase data", snapshot)
                } else {
                    Response.Success(snapshot)
                }

            }
        }
    }

    suspend fun createRecipe(recipe: Recipe) {
        return withContext(Dispatchers.IO) {
            documentReference.document(recipe.id!!).set(recipe.toMap())
        }
    }

    suspend fun updateRecipe(recipe: Recipe) {
//
//        return withContext(Dispatchers.IO) {
//            recipe.id?.let { documentReference.document(it).update(recipe.toMap()) }
//        }
    }

    suspend fun deleteRecipe(id: String) {
        return withContext(Dispatchers.IO) {
            documentReference.document(id).delete()
        }
    }
}


//fun update(recipe: Recipe) {
//
//    documentReference.document(recipe.id!!).update(recipe.toMap())
//        .addOnSuccessListener {
//            updateLiveData.postValue(true)
//        }.addOnFailureListener {
//            Log.d("update", it.localizedMessage!!)
//            updateLiveData.postValue(false)
//        }
//}