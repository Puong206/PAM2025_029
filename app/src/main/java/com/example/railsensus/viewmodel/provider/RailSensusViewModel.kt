package com.example.railsensus.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.railsensus.RailSensusApp
import com.example.railsensus.viewmodel.KeretaViewModel
import com.example.railsensus.viewmodel.LoginViewModel
import com.example.railsensus.viewmodel.LokoViewModel
import com.example.railsensus.viewmodel.SensusViewModel
import com.example.railsensus.viewmodel.UserViewModel

fun CreationExtras.railSensusApplication(): RailSensusApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RailSensusApp)

object RailSensusViewModel {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                railSensusApplication().container.repositoriRailSensus
            )
        }
        initializer {
            LokoViewModel(
                railSensusApplication().container.repositoriRailSensus
            )
        }
        initializer {
            KeretaViewModel(
                railSensusApplication().container.repositoriRailSensus
            )
        }
        initializer {
            SensusViewModel(
                railSensusApplication().container.repositoriRailSensus
            )
        }
        initializer {
            UserViewModel(
                railSensusApplication().container.repositoriRailSensus
            )
        }
    }
}