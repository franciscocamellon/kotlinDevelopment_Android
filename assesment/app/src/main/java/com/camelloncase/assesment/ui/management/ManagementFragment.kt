package com.camelloncase.assesment.ui.management

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.camelloncase.assesment.databinding.FragmentManagementBinding
import com.camelloncase.assesment.model.Recipe
import com.camelloncase.assesment.ui.login.InitialFragmentDirections
import com.camelloncase.assesment.util.formattedCurrentDate
import com.camelloncase.assesment.viewmodel.ManagementViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class ManagementFragment : Fragment() {

    private var _binding: FragmentManagementBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: ManagementViewModel
    private lateinit var recipeName: EditText
    private lateinit var recipeStyle: EditText
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar?.show()

        _binding = FragmentManagementBinding.inflate(inflater, container, false)

        verifyLoggedUser()

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

                val recipeId = UUID.randomUUID().toString()
                val name = binding.nameTextInputEditText.text.toString()
                val style = binding.styleTextInputEditText.text.toString()

                val recipe = Recipe(recipeId, recipeName = name, recipeStyle = style, recipeCreateDate = createDate)

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

    private fun verifyLoggedUser() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (user == null) {
                navigateToLoginScreen()
            }
        }, 500)
    }

    private fun goToRecipesScreen() {
        findNavController().navigate(
            ManagementFragmentDirections.actionManagementFragmentToRecipesFragment()
        )
    }
    private fun navigateToLoginScreen() {
        findNavController().navigate(
            InitialFragmentDirections.actionInitialFragmentToNavigationLogin()
        )
    }

}