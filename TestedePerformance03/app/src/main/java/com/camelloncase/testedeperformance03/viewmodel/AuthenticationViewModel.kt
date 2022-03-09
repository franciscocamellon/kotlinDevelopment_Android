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

    private val _userSignUpStatus = MutableLiveData<Resource<AuthResult>>()
    val userSignUpStatus: LiveData<Resource<AuthResult>> = _userSignUpStatus

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
            _userSignUpStatus.postValue(Resource.Error("Empty strings"))
        } else {
            _userSignUpStatus.postValue(Resource.Loading())
            viewModelScope.launch(Dispatchers.Main) {
                val loginResult = authRepository.loginUser(userEmail, userPassword)
                _userSignUpStatus.postValue(loginResult)
            }
        }
    }
}