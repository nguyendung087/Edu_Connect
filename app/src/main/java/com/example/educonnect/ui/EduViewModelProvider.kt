package com.example.educonnect.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.educonnect.EduApplication
import com.example.educonnect.ui.bookmark.BookmarkViewModel
import com.example.educonnect.ui.courses.CourseDetailsViewModel
import com.example.educonnect.ui.courses.CourseViewModel
import com.example.educonnect.ui.home.HomeViewModel
import com.example.educonnect.ui.information_form.InformationFormViewModel
import com.example.educonnect.ui.information_form.StudentInformationViewModel
import com.example.educonnect.ui.login.LoginViewModel
import com.example.educonnect.ui.mentor.MentorDetailsViewModel
import com.example.educonnect.ui.mentor.TopMentorViewModel
import com.example.educonnect.ui.notification.NotificationViewModel
import com.example.educonnect.ui.profile.ProfileEditViewModel
import com.example.educonnect.ui.profile.ProfileViewModel
import com.example.educonnect.ui.signup.SignupViewModel

object EduViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                eduApplication().container.userRepository,
                eduApplication().container.courseRepository
            )
        }
        initializer {
            SignupViewModel(
                eduApplication().container.userRepository
            )
        }
        initializer {
            InformationFormViewModel(
                this.createSavedStateHandle(),
                eduApplication().container.userRepository
            )
        }
        initializer {
            StudentInformationViewModel(
                this.createSavedStateHandle(),
                eduApplication().container.userRepository
            )
        }
        initializer {
            LoginViewModel(
                eduApplication().container.userRepository
            )
        }
        initializer {
            CourseViewModel(
                eduApplication().container.courseRepository
            )
        }
        initializer {
            CourseDetailsViewModel(
                this.createSavedStateHandle(),
                eduApplication().container.courseRepository,
                eduApplication().container.bookmarkRepository
            )
        }
        initializer {
            TopMentorViewModel(
                eduApplication().container.userRepository
            )
        }
        initializer {
            MentorDetailsViewModel(
                this.createSavedStateHandle(),
                eduApplication().container.userRepository
            )
        }

        initializer {
            BookmarkViewModel(
                eduApplication().container.bookmarkRepository,
                eduApplication().container.userRepository
            )
        }

        initializer {
            ProfileViewModel(
                eduApplication().container.userRepository
            )
        }

        initializer {
            ProfileEditViewModel(
                eduApplication().container.userRepository
            )
        }

        initializer {
            NotificationViewModel(
                eduApplication().container.userRepository,
                eduApplication().container.notificationRepository
            )
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [EduApplication].
 */
fun CreationExtras.eduApplication(): EduApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EduApplication)