package com.example.educonnect.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.educonnect.EduApplication
import com.example.educonnect.ui.home.HomeViewModel

object EduViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
//        initializer {
//            HomeViewModel(
//                this.createSavedStateHandle(),
//                eduApplication().container.userRepository,
//
//            )
//        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.eduApplication(): EduApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EduApplication)