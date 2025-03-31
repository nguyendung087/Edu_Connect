package com.example.educonnect.ui.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class SignupViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _registerUiState = MutableStateFlow<SignupUiState>(SignupUiState.Initial)
    val registerUiState : StateFlow<SignupUiState> = _registerUiState.asStateFlow()

//    private lateinit var roleState : String
//    private lateinit var uid : String
//    fun validateRole(role : String) {
//        roleState = when (role) {
//            "Giáo viên" -> "Giáo viên"
//            else -> "Học viên"
//        }
//    }

    fun registerUser(email : String, password : String, role : String) {
        Log.d("REGISTER_PARAMS", "Email: $email, Password: $password, Role: $role")
        _registerUiState.value = SignupUiState.Loading

        if (!validateInput(email, password)) {
            _registerUiState.value = SignupUiState.Error("Invalid input")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null) {
                        val uid = user.uid
                        Log.i("REGISTER_RESULT", "Đăng ký thành công với UID: $uid, $role")
                        viewModelScope.launch {
                            try {
                                userRepository.insertUserStream(
                                    User(
                                        userId = uid,
                                        email = email,
                                        role = role
                                    )
                                )
                                _registerUiState.value = SignupUiState.Success
                            } catch (e: Exception) {
                                _registerUiState.value = SignupUiState.Error("Database error: ${e.message}")
                            }
                        }
                    } else {
                        _registerUiState.value = SignupUiState.Error("User creation failed")
                    }
                } else {
                    handleFirebaseException(task.exception)

                }
            }
    }

    private suspend fun insertUserAccount(uid : String, email : String, role : String) {
        userRepository.insertUserStream(
            User(
                userId = uid,
                email = email,
                role = role
            )
        )
    }

//    fun validateRole(role : String) : String {
//        return when (role) {
//            "Giáo viên" -> "Giáo viên"
//            else -> "Học viên"
//        }
//    }


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
        _registerUiState.value = SignupUiState.Error(errorMessage)
        Log.e("REGISTER_RESULT", errorMessage)
    }
}