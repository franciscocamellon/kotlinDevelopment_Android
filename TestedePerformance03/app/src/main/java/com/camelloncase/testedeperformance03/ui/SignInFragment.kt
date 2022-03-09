package com.camelloncase.testedeperformance03.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.camelloncase.testedeperformance03.databinding.FragmentSignInBinding
import com.camelloncase.testedeperformance03.util.Resource
import com.camelloncase.testedeperformance03.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner

        auth = Firebase.auth

        binding.signUpTextView.setOnClickListener {
            navigateToSignUpScreen()
        }

        binding.recoveryAccountTextView.setOnClickListener {
            navigateToRecoveryScreen()
        }

        binding.signInAppCompatButton.setOnClickListener {

            val email = binding.emailEditText?.text.toString()
            val password = binding.passwordEditText?.text.toString()

            viewModel.loginUser(email, password)
        }

        viewModel.userSignInStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.loginProgressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.loginProgressBar.isVisible = false
                    navigateToLoggedScreen()
                    Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Error -> {
                    binding.loginProgressBar.isVisible = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        return view
    }

    private fun navigateToLoggedScreen() {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToLoggedFragment()
        )
    }

    private fun navigateToSignUpScreen() {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        )
    }

    private fun navigateToRecoveryScreen() {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToPasswordRecoveryFragment()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}