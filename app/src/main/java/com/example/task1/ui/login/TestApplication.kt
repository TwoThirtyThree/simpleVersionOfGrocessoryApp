package com.example.task1.ui.login

import android.app.Application
import com.google.firebase.FirebaseApp


class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}