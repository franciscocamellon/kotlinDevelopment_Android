package com.camelloncase.testedeperformance03.ui.management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.camelloncase.testedeperformance03.databinding.FragmentManagementBinding
import com.camelloncase.testedeperformance03.model.Recipe
import com.camelloncase.testedeperformance03.util.formattedCurrentDate
import com.camelloncase.testedeperformance03.viewmodel.ManagementViewModel

class ManagementFragment : Fragment() {

    private var _binding: FragmentManagementBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: ManagementViewModel
    private lateinit var recipeName: EditText
    private lateinit var recipeStyle: EditText
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentManagementBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this)[ManagementViewModel::class.java]

        initComponents()

        val args = ManagementFragmentArgs.fromBundle(requireArguments())

        if (args.actionType == "update") {

            val updateDate = formattedCurrentDate("dd-MMM-yy hh:mm")

            binding.nameTextInputEditText.setText(args.recipeName)
            binding.styleTextInputEditText.setText(args.recipeStyle)
            binding.createDateTextInputEditText.setText(args.recipeCreateDate)
            binding.createDateTextInputEditText.isEnabled = false
            binding.updateDateTextInputEditText.setText(updateDate)
            binding.updateDateTextInputEditText.isEnabled = false
            binding.submitButton.text = "Update"

            binding.submitButton.setOnClickListener {

                val name = binding.nameTextInputEditText.text.toString()
                val style = binding.styleTextInputEditText.text.toString()

                val recipe = Recipe(args.id, name, style, args.recipeCreateDate, updateDate)

                viewModel.update(recipe)

                goToRecipesScreen()

            }
        } else {

            val createDate = formattedCurrentDate("dd-MMM-yy hh:mm")

            binding.createDateTextInputEditText.setText(createDate)
            binding.createDateTextInputEditText.isEnabled = false
            binding.updateDateTextInputEditText.isEnabled = false

            binding.submitButton.setOnClickListener {

                val name = binding.nameTextInputEditText.text.toString()
                val style = binding.styleTextInputEditText.text.toString()

                val recipe = Recipe(recipeName = name, recipeStyle = style, recipeCreateDate = createDate)

                viewModel.create(recipe)

                goToRecipesScreen()

            }
        }

        binding.cancelButton.setOnClickListener {
            goToRecipesScreen()
        }

        return binding.root
    }

    private fun initComponents() {
        recipeName = binding.nameTextInputEditText
        recipeStyle = binding.styleTextInputEditText
        submitButton = binding.submitButton
        cancelButton = binding.cancelButton
    }

    private fun goToRecipesScreen() {
        findNavController().navigate(
            ManagementFragmentDirections.actionManagementFragmentToRecipesFragment()
        )
    }

}