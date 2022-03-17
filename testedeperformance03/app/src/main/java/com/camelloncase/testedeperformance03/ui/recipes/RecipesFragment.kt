package com.camelloncase.testedeperformance03.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.adapter.RecipeAdapter
import com.camelloncase.testedeperformance03.databinding.FragmentRecipesBinding
import com.camelloncase.testedeperformance03.model.Recipe
import com.camelloncase.testedeperformance03.ui.management.ManagementFragmentDirections
import com.camelloncase.testedeperformance03.viewmodel.RecipeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipesFragment : Fragment(), RecipeAdapter.OnItemClickListener {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var addFloatingActionButton: FloatingActionButton
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: RecipeViewModel
    private lateinit var recipeAdapter: RecipeAdapter
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

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[RecipeViewModel::class.java]

        recipesRecyclerView = binding.recipesRecyclerView
        recipesRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getList()

        addFloatingActionButton = binding.addFloatingActionButton

        addFloatingActionButton.setOnClickListener {
            goToManagementScreen()
        }

        viewModel.getListLiveData.observe(viewLifecycleOwner, Observer {
            onGetList(it)
        })


        return binding.root
    }

    private fun onGetList(it: List<Recipe>) {
        recipesList = ArrayList()
        recipesList.addAll(it)

        recipeAdapter = RecipeAdapter(recipesList, this)

        recipesRecyclerView.adapter = recipeAdapter
        recipesRecyclerView.layoutManager = LinearLayoutManager(context)

        recipeAdapter.notifyDataSetChanged()
    }

    override fun onClick(item: Recipe, position: Int) {
        goToManagementScreen("update", item.recipeName, item.recipeStyle, item.recipeCreateDate)

    }

    override fun onDelete(item: Recipe, position: Int) {
        TODO("Not yet implemented")
    }

    private fun goToManagementScreen(actionType: String? = null, name: String? = null, style: String? = null, date: String? = null) {

        findNavController().navigate(
            RecipesFragmentDirections
                .actionRecipesFragmentToManagementFragment(
                    actionType,
                    name,
                    style,
                    date
                )
        )
    }


}