package com.example.educonnect

import android.app.Application
import android.util.Log
import com.example.educonnect.data.database.AppContainer
import com.example.educonnect.data.database.AppDataContainer
import com.google.firebase.FirebaseApp
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class EduApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    lateinit var supabase: SupabaseClient
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        initFirebase()
        initSupabase()
        initContainer()
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initSupabase() {
        applicationScope.launch {
            supabase = createSupabaseClient(
                supabaseUrl = "https://zpdurqxwndlkjcvagvqu.supabase.co",
                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InpwZHVycXh3bmRsa2pjdmFndnF1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcyMTQyNjgsImV4cCI6MjA2Mjc5MDI2OH0.nWEUvK2n5yIpp-OPIMlZsWFclxZjf_SyCh0fHG6msbU"
            ) {
                install(Storage)
                install(Postgrest)
            }
            Log.d("EduApplication", "Supabase initialization complete")
        }
    }

    private fun initContainer() {
        applicationScope.launch {
            container = AppDataContainer(this@EduApplication)
        }
    }
}