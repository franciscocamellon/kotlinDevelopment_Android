package com.camelloncase.testedeperformance03.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.camelloncase.testedeperformance03.R
import com.camelloncase.testedeperformance03.databinding.FragmentSignInBinding
import com.camelloncase.testedeperformance03.databinding.FragmentSignUpBinding
import com.camelloncase.testedeperformance03.util.Resource
import com.camelloncase.testedeperformance03.viewmodel.AuthenticationViewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up,container,false)

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]

        binding.authViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.createUser(email, password)
        }

        viewModel.userRegistrationStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.registerProgressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.registerProgressBar.isVisible = false
                    Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Error -> {
                    binding.registerProgressBar.isVisible = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        return binding.root
    }


}