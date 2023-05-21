package com.example.composeapp

import android.app.Application
import com.example.composeapp.di.AppComponent
import com.example.composeapp.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().build()
    }
}