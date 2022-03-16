package com.camelloncase.testedeperformance03.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.adapter.RecipeAdapter
import com.camelloncase.testedeperformance03.databinding.FragmentRecipesBinding
import com.camelloncase.testedeperformance03.model.Recipe
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var addFloatingActionButton: FloatingActionButton
    private lateinit var recipeName: EditText
    private lateinit var recipeStyle: EditText
    private lateinit var recipeCreateDate: EditText
    private lateinit var recipesList: ArrayList<Recipe>
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        recipesRecyclerView = binding.recipesRecyclerView
        recipesRecyclerView.layoutManager = LinearLayoutManager(context)
        val recipesRecyclerViewAdapter = RecipeAdapter()


        addFloatingActionButton = binding.addFloatingActionButton

        addFloatingActionButton.setOnClickListener {
            findNavController().navigate(
                RecipesFragmentDirections.actionRecipesFragmentToManagementFragment()
            )
        }


        return binding.root
    }

    private fun getList():ArrayList<Recipe> {
        val documentReference = db.collection("Recipes")
        documentReference.get().addOnSuccessListener {
            val recipes = ArrayList<Recipe>()
            for (item in it.documents) {
                val recipe = Recipe()
                recipe.recipeName = item.data!!["name"] as String?
                recipe.recipeStyle = item.data!!["style"] as String?
                recipe.recipeCreateDate = item.data!!["create_date"] as String?
                recipes.add(recipe)
            }
        }.addOnFailureListener {
            Log.d("get", it.localizedMessage!!)
        }
    }


}