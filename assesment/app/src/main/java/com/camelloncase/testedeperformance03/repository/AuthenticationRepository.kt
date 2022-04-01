package com.camelloncase.testedeperformance03.repository

import com.camelloncase.testedeperformance03.util.Resource
import com.camelloncase.testedeperformance03.util.safeCall
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthenticationRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun createUser(userEmail: String, userPassword: String): Resource<AuthResult> {

        return withContext(Dispatchers.IO) {

            safeCall {

                val registrationResult = firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).await()

                Resource.Success(registrationResult)
            }
        }

    }

    suspend fun loginUser(email: String, password: String): Resource<AuthResult> {

        return withContext(Dispatchers.IO) {

            safeCall {

                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

                Resource.Success(result)
            }
        }
    }

    suspend fun recoverPassword(email: String) {

        return withContext(Dispatchers.IO) {

            firebaseAuth.sendPasswordResetEmail(email).await()

        }
    }
}