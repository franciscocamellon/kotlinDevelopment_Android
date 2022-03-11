package com.camelloncase.testedeperformance03.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.camelloncase.testedeperformance03.databinding.FragmentLoginBinding
import com.camelloncase.testedeperformance03.util.Resource
import com.camelloncase.testedeperformance03.util.showMessageToUser
import com.camelloncase.testedeperformance03.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]

        auth = Firebase.auth

        binding.signUpTextView.setOnClickListener {
            navigateToRegisterScreen()
        }

        binding.recoveryAccountTextView.setOnClickListener {
            navigateToRecoverScreen()
        }

        binding.signInAppCompatButton.setOnClickListener {

            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            when {
                email.isEmpty() -> showMessageToUser(context, "Email is empty!")
                password.isEmpty() -> showMessageToUser(context, "Password is empty!")
                else -> viewModel.loginUser(email, password)
            }
        }

        viewModel.userSignInStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    enableProgressBar(true)
                }
                is Resource.Success -> {
                    enableProgressBar(false)
                    navigateToHomeScreen()
                    showMessageToUser(context, "Logged In Successfully")
                }
                is Resource.Error -> {
                    enableProgressBar(false)
                    showMessageToUser(context, it.message.toString())
                }
            }
        })

        return binding.root
    }

    private fun navigateToRegisterScreen() {
        findNavController().navigate(
            LoginFragmentDirections.actionNavigationLoginToNavigationRegister()
        )
    }

    private fun navigateToRecoverScreen() {
        findNavController().navigate(
            LoginFragmentDirections.actionNavigationLoginToNavigationRecover()
        )
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(
            LoginFragmentDirections.actionNavigationLoginToNavigationHome()
        )
    }

    private fun enableProgressBar(choice: Boolean) {
        binding.loginProgressBar.isVisible = choice
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}