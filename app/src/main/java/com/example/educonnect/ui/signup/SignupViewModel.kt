package com.example.educonnect.ui.signup

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class SignupViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

//    private var _registerUiState = MutableStateFlow(SignupUiState())
//    val registerUiState : StateFlow<SignupUiState> = _registerUiState.asStateFlow()

    fun registerUser(email : String, password : String, role : String) {
        if (!validateInput(email, password)) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    user?.let {
                        val uid = user.uid
                        Log.i("REGISTER_RESULT", "Đăng ký thành công với UID: $uid")
                        viewModelScope.launch {
                            try {
                                userRepository.insertUserStream(
                                    User(
                                        userId = uid,
                                        email = email,
                                        role = role
                                    )
                                )
//                                _registerUiState.update { currentState ->
//                                    currentState.copy(
//                                        role = role
//                                    )
//                                }
                                Log.d("SIGNUP_DEBUG", "Registered UID: ${user.uid}")
                            } catch (e: Exception) {
                                Log.e("REGISTER_RESULT", "Lỗi: $e")
                            }
                        }
                    } ?: run {
                        Log.e("REGISTER_RESULT", "Lỗi")
                    }
                } else {
                    handleFirebaseException(task.exception)

                }
            }
    }

    private fun validateInput(email : String, password : String): Boolean {
        return when {
            email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> false
            password.length < 6 -> false
            else -> true
        }
    }

    private fun handleFirebaseException(exception: Exception?) {
        val errorMessage = when (exception) {
            is FirebaseAuthUserCollisionException -> "Email already exists"
            is FirebaseAuthInvalidCredentialsException -> "Invalid email format"
            else -> "Registration failed: ${exception?.message ?: "Unknown error"}"
        }
        Log.e("REGISTER_RESULT", errorMessage)
    }
}