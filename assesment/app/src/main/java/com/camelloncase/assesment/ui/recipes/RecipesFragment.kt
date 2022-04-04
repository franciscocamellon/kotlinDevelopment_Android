package com.camelloncase.assesment.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.assesment.adapter.RecipeAdapter
import com.camelloncase.assesment.databinding.FragmentRecipesBinding
import com.camelloncase.assesment.model.Recipe
import com.camelloncase.assesment.util.Response
import com.camelloncase.assesment.util.showMessageToUser
import com.camelloncase.assesment.viewmodel.RecipesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.QuerySnapshot

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

        viewModel.getAllRecipes()

        addFloatingActionButton = binding.addFloatingActionButton

        addFloatingActionButton.setOnClickListener {
            goToCreateScreen()
        }

//        viewModel.getListLiveData.observe(viewLifecycleOwner, Observer {
//            onGetList(it)
//        })

        viewModel.recipeLoadingStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Response.Loading -> {
                    enableProgressBar(true)
                }
                is Response.Success -> {
                    enableProgressBar(false)
                    onGetListAsync(it)
                    showMessageToUser(context, "Recipes successfully loaded")
                }
                is Response.Failure -> {
                    enableProgressBar(false)
                    showMessageToUser(context, it.message.toString())
                }
            }
        })

        viewModel.deleteLiveData.observe(viewLifecycleOwner, Observer  {
            viewModel.getAllRecipes()
        })

        return binding.root
    }

    private fun onGetListAsync(it: Response<QuerySnapshot>) {
        recipesList = ArrayList()
        for (item in it.data!!) {
            val recipe = Recipe()
            recipe.id = item.data["id"] as String?
            recipe.recipeName = item.data["name"] as String?
            recipe.recipeStyle = item.data["style"] as String?
            recipe.recipeCreateDate = item.data["create_date"] as String?
            recipe.recipeUpdateDate = item.data["update_date"] as String?
            recipesList.add(recipe)
        }

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

    private fun enableProgressBar(choice: Boolean) {
        binding.recipesLoadingProgressBar.isVisible = choice
    }

}