package com.example.educonnect

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.educonnect.data.SampleData
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import com.example.educonnect.ui.EduApp
import com.example.educonnect.ui.students_screens.courses.CourseDetails
import com.example.educonnect.ui.signup.SignupScreen
import com.example.educonnect.ui.theme.EduConnectTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAuth.getInstance().signOut()
        val appContainer = (application as EduApplication).container
        setContent {
            EduConnectTheme {
                EduApp(
                    appContainer = appContainer
                )
            }
        }
    }
}
