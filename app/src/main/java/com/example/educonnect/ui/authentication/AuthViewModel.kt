package com.example.educonnect.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.User
import com.example.educonnect.ui.signup.SignUpDestination
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//class AuthViewModel(
//    private val userRepository: UserRepository
//) : ViewModel() {
//    private val auth = FirebaseAuth.getInstance()
//
//    private var _authUiState = MutableStateFlow(AuthUiState())
//    val authUiState : StateFlow<AuthUiState> = _authUiState.asStateFlow()
//
//    init {
//        auth.addAuthStateListener { firebaseAuth ->
//            firebaseAuth.currentUser?.uid?.let { uid ->
//                viewModelScope.launch {
//                    userRepository.getUserStream(uid).collect { user ->
//                        _authUiState.update { currentState ->
//                            currentState.copy(
//                                currentUser = user,
//                                isLoading = false
//                            )
//                        }
//                    }
//                }
//            } ?: run {
//                _authUiState.update { currentState ->
//                    currentState.copy(
//                        currentUser = null,
//                        isLoading = true
//                    )
//                }
//            }
//        }
//    }
//
//    fun logout(navController: NavController) {
//        auth.signOut()
//        viewModelScope.launch {
////            userRepository.clearUserData()
//        }
//        navController.navigate(SignUpDestination.route) {
//            popUpTo(0)
//        }
//    }
//}
//
//data class AuthUiState(
//    val currentUser : User? = null,
//    val isLoading : Boolean = true
//)