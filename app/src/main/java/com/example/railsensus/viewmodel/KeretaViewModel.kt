package com.example.railsensus.viewmodel

import androidx.lifecycle.ViewModel
import com.example.railsensus.modeldata.Kereta
import com.example.railsensus.modeldata.StatistikKereta
import com.example.railsensus.modeldata.UIKeretaState
import com.example.railsensus.repositori.RepositoriRailSensus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KeretaViewModel(
    private val repositori: RepositoriRailSensus): ViewModel() {

    //State
    private val _keretaList = MutableStateFlow<List<Kereta>>(emptyList())
    val keretaList: StateFlow<List<Kereta>> = _keretaList.asStateFlow()

    private val _keretaFormState = MutableStateFlow(UIKeretaState())
    val keretaFormState: StateFlow<UIKeretaState> = _keretaFormState.asStateFlow()

    private val _selectedKereta = MutableStateFlow<Kereta?>(null)
    val selectedKereta: StateFlow<Kereta?> = _selectedKereta.asStateFlow()

    private val _statistik = MutableStateFlow<StatistikKereta?>(null)
    val statistik: StateFlow<StatistikKereta?> = _statistik.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
}