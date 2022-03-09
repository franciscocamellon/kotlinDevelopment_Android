package com.camelloncase.testedeperformance03.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.camelloncase.testedeperformance03.R
import com.camelloncase.testedeperformance03.databinding.FragmentLoggedBinding
import com.camelloncase.testedeperformance03.databinding.FragmentSignUpBinding

class LoggedFragment : Fragment() {

    private var _binding: FragmentLoggedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_logged,container,false)

        binding.signOutImageView.setOnClickListener {
            navigateToLoginScreen()
        }

        return binding.root
    }

    private fun navigateToLoginScreen() {
        findNavController().navigate(
            LoggedFragmentDirections.actionLoggedFragmentToSignInFragment()
        )
    }

}