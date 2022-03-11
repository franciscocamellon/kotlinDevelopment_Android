package com.camelloncase.testedeperformance03.ui.recover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.camelloncase.testedeperformance03.databinding.FragmentRecoverBinding
import com.camelloncase.testedeperformance03.util.showMessageToUser
import com.camelloncase.testedeperformance03.viewmodel.AuthenticationViewModel


class RecoverFragment : Fragment() {

    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        _binding = FragmentRecoverBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]

        binding.backImageView.setOnClickListener {
            navigateToLoginScreen()
        }

        binding.sendEmailAppCompatButton.setOnClickListener {

            val email = binding.emailEditText.text.toString()

            viewModel.recoverPassword(email)
        }

        viewModel.passwordRecoverStatus.observe(viewLifecycleOwner, Observer {

            when(it) {
                true -> navigateToLoginScreen()
                else -> {
                    showMessageToUser(application, viewModel.error.toString())
                    navigateToLoginScreen()
                }
            }
        })

        return binding.root
    }

    private fun navigateToLoginScreen() {
        findNavController().navigate(
            RecoverFragmentDirections.actionNavigationRecoverToNavigationLogin()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}