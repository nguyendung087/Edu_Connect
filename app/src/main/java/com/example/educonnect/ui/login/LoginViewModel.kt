package com.example.educonnect.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.LoginRepository
import com.example.educonnect.data.Result

import com.example.educonnect.R
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
//    private val auth = FirebaseAuth.getInstance()
////    private val userRepository: UserRepository = // Initialize UserRepository
//
//        // State to track registration success/failure
//        private val _registrationStatus = MutableStateFlow<RegistrationStatus>(RegistrationStatus.Idle)
//    val registrationStatus: StateFlow<RegistrationStatus> = _registrationStatus
//
//    sealed class RegistrationStatus {
//        object Idle : RegistrationStatus()
//        object Loading : RegistrationStatus()
//        data class Success(val role: String) : RegistrationStatus()
//        data class Error(val message: String) : RegistrationStatus()
//    }
//
//    fun registerUser(email: String, password: String, role: String) {
//        viewModelScope.launch {
//            _registrationStatus.value = RegistrationStatus.Loading
//            try {
//                auth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            val user = auth.currentUser
//                            if (user != null) {
//                                // Save user info to Room Database
//                                val newUser = User(
//                                    firebaseUid = user.uid,
//                                    email = email,
//                                    role = role
//                                )
//                                userRepository.insertUserStream(newUser)
//                                _registrationStatus.value = RegistrationStatus.Success(role)
//                            }
//                        } else {
//                            _registrationStatus.value = RegistrationStatus.Error(task.exception?.message ?: "Registration failed")
//                        }
//                    }
//            } catch (e: Exception) {
//                _registrationStatus.value = RegistrationStatus.Error(e.message ?: "An error occurred")
//            }
//        }
//    }





    
//    private val _loginForm = MutableLiveData<LoginFormState>()
//    val loginFormState: LiveData<LoginFormState> = _loginForm
//
//    private val _loginResult = MutableLiveData<LoginResult>()
//    val loginResult: LiveData<LoginResult> = _loginResult
//
//    fun login(username: String, password: String) {
//        // can be launched in a separate asynchronous job
//        val result = loginRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
////            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
//    }
//
//    fun loginDataChanged(username: String, password: String) {
//        if (!isUserNameValid(username)) {
////            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
//        } else if (!isPasswordValid(password)) {
////            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
//        } else {
//            _loginForm.value = LoginFormState(isDataValid = true)
//        }
//    }
//
//    // A placeholder username validation check
//    private fun isUserNameValid(username: String): Boolean {
//        return if (username.contains('@')) {
//            Patterns.EMAIL_ADDRESS.matcher(username).matches()
//        } else {
//            username.isNotBlank()
//        }
//    }
//
//    // A placeholder password validation check
//    private fun isPasswordValid(password: String): Boolean {
//        return password.length > 5
//    }
}