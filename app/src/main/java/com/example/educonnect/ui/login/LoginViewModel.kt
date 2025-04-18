package com.example.educonnect.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private var _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState : StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun loginUser(email: String, password: String) {
        if (!validateInput(email, password)) {
            updateUiState(false, "Email hoặc mật khẩu không hợp lệ")
            return
        }

        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.i("LOGIN_RESULT", "Đăng nhập thành công với UID: ${task.result?.user?.uid}")
                            updateUiState(true, null)
                        } else {
                            handleFirebaseException(task.exception)
                        }
                    }
            } catch (e: Exception) {
                updateUiState(false, e.message ?: "Đã xảy ra lỗi")
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            updateUiState(false, "Đã gửi email đặt lại mật khẩu")
                        } else {
                            updateUiState(false, task.exception?.message ?: "Gửi email thất bại")
                        }
                    }
            } catch (e: Exception) {
                updateUiState(false, e.message ?: "Đã xảy ra lỗi")
            }
        }
    }

    fun logout() {
        auth.signOut()
        updateUiState(isLoggedIn = false, errorMessage = "Đã đăng xuất")
    }

    private fun updateUiState(isLoggedIn : Boolean, errorMessage : String?) {
        _loginUiState.update { currentState ->
            currentState.copy(
                isLoggedIn = isLoggedIn,
                errorMessage = errorMessage
            )
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
        updateUiState(false, errorMessage)
        Log.e("LOGIN_RESULT", errorMessage)
    }
}

data class LoginUiState(
    val isLoggedIn : Boolean = false,
    val errorMessage: String? = null
)