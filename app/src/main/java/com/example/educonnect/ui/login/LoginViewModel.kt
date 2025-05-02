package com.example.educonnect.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private var _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
    val loginUiState : StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun loginUser(email: String, password: String) {
        if (!validateInput(email, password)) {
            _loginUiState.value = LoginUiState.Error("Email hoặc mật khẩu không hợp lệ")
            return
        }

        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = task.result?.user?.uid
                            Log.i("LOGIN_RESULT", "Đăng nhập thành công với UID: $uid")
                            _loginUiState.value = LoginUiState.Success
                            sendFcmTokenToServer(uid)
                        } else {
                            handleFirebaseException(task.exception)
                        }
                    }
            } catch (e: Exception) {
                _loginUiState.value = LoginUiState.Error(e.message ?: "Đã xảy ra lỗi")
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _loginUiState.value = LoginUiState.Success
                            Log.i("FORGOT_PASSWORD", "Đã gửi email đặt lại mật khẩu")
                        } else {
                            _loginUiState.value = LoginUiState.Error(task.exception?.message ?: "Gửi email thất bại")
                        }
                    }
            } catch (e: Exception) {
                _loginUiState.value = LoginUiState.Error(e.message ?: "Đã xảy ra lỗi")
            }
        }
    }

    private fun sendFcmTokenToServer(userId: String?) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                viewModelScope.launch {
                    // TODO: Send token to server with userId
                    Log.d("FCM_TOKEN", "Token for user $userId: $token")
                }
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return email.isNotBlank() && password.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun handleFirebaseException(exception: Exception?) {
        val errorMessage = when (exception) {
            is FirebaseAuthInvalidCredentialsException -> "Thông tin đăng nhập không đúng"
            is FirebaseAuthInvalidUserException -> "Tài khoản không tồn tại"
            else -> "Đăng nhập thất bại: ${exception?.message ?: "Lỗi không xác định"}"
        }
        _loginUiState.value = LoginUiState.Error(errorMessage)
        Log.e("LOGIN_RESULT", errorMessage)
    }
}

sealed class LoginUiState {
    data object Success : LoginUiState()
    data object Loading : LoginUiState()
    data class Error(val message : String) : LoginUiState()
}