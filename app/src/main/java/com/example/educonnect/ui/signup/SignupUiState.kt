package com.example.educonnect.ui.signup

sealed class SignupUiState {
    data object Initial : SignupUiState()
    data object Loading : SignupUiState()
    data object Success : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}
