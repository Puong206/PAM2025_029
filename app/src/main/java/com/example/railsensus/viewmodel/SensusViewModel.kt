package com.example.railsensus.viewmodel

import androidx.lifecycle.ViewModel
import com.example.railsensus.modeldata.Sensus
import com.example.railsensus.modeldata.UISensusState
import com.example.railsensus.repositori.RepositoriRailSensus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SensusViewModel(
    private val repositori: RepositoriRailSensus
): ViewModel() {
    //list sensus
    private val _sensusList = MutableStateFlow<List<Sensus>>(emptyList())
    val sensusList: StateFlow<List<Sensus>> = _sensusList.asStateFlow()

    // Form state
    private val _sensusFormState = MutableStateFlow(UISensusState())
    val sensusFormState: StateFlow<UISensusState> = _sensusFormState.asStateFlow()

    // Selected sensus (for detail)
    private val _selectedSensus = MutableStateFlow<Sensus?>(null)
    val selectedSensus: StateFlow<Sensus?> = _selectedSensus.asStateFlow()

    // Pagination
    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    // Loading & Error
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Vote loading (separate from main loading)
    private val _isVoting = MutableStateFlow(false)
    val isVoting: StateFlow<Boolean> = _isVoting.asStateFlow()
}