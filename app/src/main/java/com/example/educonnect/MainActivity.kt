package com.example.educonnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.educonnect.ui.EduApp
import com.example.educonnect.ui.courses.CourseDetails
import com.example.educonnect.ui.theme.EduConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EduConnectTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) {
//                }
            }
        }
    }
}
