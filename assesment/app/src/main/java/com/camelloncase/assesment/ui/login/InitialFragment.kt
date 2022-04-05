package com.camelloncase.assesment.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.camelloncase.assesment.databinding.FragmentInitialBinding
import com.google.firebase.auth.FirebaseAuth

class InitialFragment : Fragment() {

    private var _binding: FragmentInitialBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar?.show()

        _binding = FragmentInitialBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        verifyLoggedUser()

        onApiButtonClick()

        onCrudButtonClick()

        onLogOutButtonClick()

        return binding.root
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

    private fun onApiButtonClick() {
        binding.apiButton.setOnClickListener {
            navigateToApiScreen()
        }
    }

    private fun onCrudButtonClick() {
        binding.crudButton.setOnClickListener {
            navigateToRecipesScreen()
        }
    }

    private fun onLogOutButtonClick() {
        binding.logoutButton.setOnClickListener {
            logOut()
            navigateToLoginScreen()
        }
    }

    private fun logOut() {
        auth.signOut()
    }


    private fun navigateToRecipesScreen() {
        findNavController().navigate(
            InitialFragmentDirections.actionInitialFragmentToRecipesFragment()
        )
    }

    private fun navigateToApiScreen() {
        findNavController().navigate(
            InitialFragmentDirections.actionInitialFragmentToApiConsumptionFragment()
        )
    }

    private fun navigateToLoginScreen() {
        findNavController().navigate(
            InitialFragmentDirections.actionInitialFragmentToNavigationLogin()
        )
    }
}