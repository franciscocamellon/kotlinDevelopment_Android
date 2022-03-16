package com.camelloncase.testedeperformance03.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.adapter.RecipesRecyclerViewAdapter
import com.camelloncase.testedeperformance03.databinding.FragmentRecipesBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: RecipeViewModel
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var addFloatingActionButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[RecipeViewModel::class.java]

        addFloatingActionButton = binding.addFloatingActionButton
        recipesRecyclerView = binding.recipesRecyclerView
        recipesRecyclerView.layoutManager = LinearLayoutManager(application)

        val recipeList = viewModel.getListLiveData.value

        val recipesRecyclerViewAdapter = recipeList?.let {
            RecipesRecyclerViewAdapter(
                it,
                RecipesRecyclerViewAdapter.OnItemClickListener {
                    viewModel.updateLiveData
                })
        }
        recipesRecyclerView.adapter = recipesRecyclerViewAdapter

        binding.addFloatingActionButton.setOnClickListener {
            goToRecipeManagementFragment()
        }

        return binding.root
    }

    private fun goToRecipeManagementFragment() {

        val action = RecipesFragmentDirections.actionRecipesFragmentToRecipeManagementActivity()
        action.arguments[]
        findNavController().navigate(

        )
    }

}