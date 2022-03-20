package com.camelloncase.testedeperformance03.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.adapter.RecipeAdapter
import com.camelloncase.testedeperformance03.databinding.FragmentRecipesBinding
import com.camelloncase.testedeperformance03.model.Recipe
import com.camelloncase.testedeperformance03.viewmodel.RecipesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipesFragment : Fragment(), RecipeAdapter.OnItemClickListener {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var addFloatingActionButton: FloatingActionButton
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: RecipesViewModel
    private lateinit var recipesList: ArrayList<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[RecipesViewModel::class.java]

        recipesRecyclerView = binding.recipesRecyclerView
        recipesRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getList()

        addFloatingActionButton = binding.addFloatingActionButton

        addFloatingActionButton.setOnClickListener {
            goToCreateScreen()
        }

        viewModel.getListLiveData.observe(viewLifecycleOwner, Observer {
            onGetList(it)
        })

        viewModel.deleteLiveData.observe(viewLifecycleOwner, Observer  {
            viewModel.getList()
        })

        return binding.root
    }

    private fun onGetList(it: List<Recipe>) {
        recipesList = ArrayList()
        recipesList.addAll(it)

        recipeAdapter = RecipeAdapter(recipesList, this)
        recipesRecyclerView.adapter = recipeAdapter

        recipeAdapter.notifyDataSetChanged()
    }

    private fun goToManagementScreen(recipe: Recipe) {

        val action = RecipesFragmentDirections.actionRecipesFragmentToManagementFragment()
        action.actionType = "update"
        action.id = recipe.id.toString()
        action.recipeName = recipe.recipeName.toString()
        action.recipeStyle = recipe.recipeStyle.toString()
        action.recipeCreateDate = recipe.recipeCreateDate.toString()

        findNavController().navigate(action)
    }

    private fun goToCreateScreen() {
        val action = RecipesFragmentDirections.actionRecipesFragmentToManagementFragment()
        findNavController().navigate(action)
    }

    override fun onClick(recipe: Recipe, position: Int) {
        goToManagementScreen(recipe)
    }

    override fun onDelete(recipe: Recipe, position: Int) {
        viewModel.delete(recipe.id!!)
    }

}