package com.example.railsensus

import android.app.Application
import com.example.railsensus.repositori.AppContainer
import com.example.railsensus.repositori.ContainerApp

class RailSensusApp : Application() {
    
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ContainerApp()

    }
}
