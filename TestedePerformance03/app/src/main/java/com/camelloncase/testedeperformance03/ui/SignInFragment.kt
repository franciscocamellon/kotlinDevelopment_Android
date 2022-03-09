package com.camelloncase.testedeperformance03.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.camelloncase.testedeperformance03.databinding.FragmentSignInBinding
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
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        val view = binding.root

        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            )
        }

//        binding.signInAppCompatButton.setOnClickListener {
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()
//
//            when {
//                email.isEmpty() || password.isEmpty() -> {
//                    Toast.makeText(context, "Email or password is empty.", Toast.LENGTH_SHORT).show()
//                }
//                else -> {
//                    awaitSignIn(auth, email,password)
//                }
//            }
//        }

        return view
    }

    suspend fun awaitSignIn(auth: FirebaseAuth, email: String, password: String): AuthResult? {
        return try {
            val data = auth.signInWithEmailAndPassword(email, password).await()
            data
        } catch (e: Exception) {
            null
        }
    }

//    private fun reload() {
//        findNavController().navigate(
//            com.camelloncase.testedeperformance03.SignInFragmentDirections.actionSignInFragmentToLoggedFragment()
//        )
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}