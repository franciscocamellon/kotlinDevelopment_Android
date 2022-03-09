package com.camelloncase.testedeperformance03.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camelloncase.testedeperformance03.repository.AuthenticationRepository
import com.camelloncase.testedeperformance03.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel: ViewModel() {

    private val _userRegistrationStatus = MutableLiveData<Resource<AuthResult>>()
    val userRegistrationStatus: LiveData<Resource<AuthResult>> = _userRegistrationStatus

    private val _userSignInStatus = MutableLiveData<Resource<AuthResult>>()
    val userSignInStatus: LiveData<Resource<AuthResult>> = _userSignInStatus

    private val authRepository = AuthenticationRepository()

    fun createUser(userEmail: String, userPassword: String) {
        var error =
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                "Empty strings"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                "Not a valid email"
            } else null

        error?.let {
            _userRegistrationStatus.postValue(Resource.Error(it))
            return
        }

        _userRegistrationStatus.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.Main) {
            val registerResult = authRepository.createUser(userEmail, userPassword)
            _userRegistrationStatus.postValue(registerResult)
        }
    }

    fun loginUser(userEmail: String, userPassword: String) {
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            _userSignInStatus.postValue(Resource.Error("Empty strings"))
        } else {
            _userSignInStatus.postValue(Resource.Loading())
            viewModelScope.launch(Dispatchers.Main) {
                val loginResult = authRepository.loginUser(userEmail, userPassword)
                _userSignInStatus.postValue(loginResult)
            }
        }
    }
}