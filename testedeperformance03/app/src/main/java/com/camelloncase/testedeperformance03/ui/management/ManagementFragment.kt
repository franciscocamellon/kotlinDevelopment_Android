package com.camelloncase.testedeperformance03.ui.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.camelloncase.testedeperformance03.R
import com.camelloncase.testedeperformance03.databinding.FragmentManagementBinding
import com.camelloncase.testedeperformance03.model.Recipe
import com.camelloncase.testedeperformance03.ui.recipes.RecipesFragmentArgs
import com.camelloncase.testedeperformance03.util.formattedCurrentDate
import com.camelloncase.testedeperformance03.viewmodel.RecipeViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ManagementFragment : Fragment() {

    private var _binding: FragmentManagementBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: RecipeViewModel
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentManagementBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[RecipeViewModel::class.java]

        val args = RecipesFragmentArgs.fromBundle(requireArguments())

        if (args.actionType == "update") {
            binding.nameTextInputEditText.setText(args.recipeName.toString())
            binding.styleTextInputEditText.setText(args.recipeStyle.toString())
        }

        binding.submitButton.setOnClickListener {

            val name = binding.nameTextInputEditText.text.toString()
            val style = binding.styleTextInputEditText.text.toString()
            val date = formattedCurrentDate("dd MMM yy - hh:mm")

            val recipe = Recipe(name, style, date)

            viewModel.create(recipe)
            goToRecipesScreen()

        }

        binding.cancelButton.setOnClickListener {
            goToRecipesScreen()
        }

        return binding.root
    }

    private fun goToRecipesScreen() {
        findNavController().navigate(
            ManagementFragmentDirections.actionManagementFragmentToRecipesFragment()
        )
    }

}