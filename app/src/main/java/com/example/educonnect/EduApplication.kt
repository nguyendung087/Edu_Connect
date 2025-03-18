package com.example.educonnect

import android.app.Application
import com.example.educonnect.data.database.AppContainer
import com.example.educonnect.data.database.AppDataContainer

class EduApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}