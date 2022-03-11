package com.camelloncase.testedeperformance03.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.camelloncase.testedeperformance03.databinding.FragmentLoginBinding
import com.camelloncase.testedeperformance03.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.homeTextView.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionNavigationLoginToNavigationHome()
            )
        }

        binding.registerTextView.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionNavigationLoginToNavigationRegister()
            )
        }

        binding.recoverTextView.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionNavigationLoginToNavigationRecover()
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}