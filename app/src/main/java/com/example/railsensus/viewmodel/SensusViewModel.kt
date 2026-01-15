package com.example.railsensus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.railsensus.modeldata.Sensus
import com.example.railsensus.modeldata.UISensusState
import com.example.railsensus.modeldata.isValid
import com.example.railsensus.modeldata.toCreateRequest
import com.example.railsensus.repositori.ApiResult
import com.example.railsensus.repositori.RepositoriRailSensus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    //helper
    fun resetForm() {
        _sensusFormState.value = UISensusState()
        _selectedSensus.value = null
    }

    fun clearError() {
        _errorMessage.value = null
        _sensusFormState.update { it.copy(errorMessage = null) }
    }

    //load all
    fun loadAllSensus(page: Int = 1, limit: Int = 20) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = repositori.getAllSensus(page, limit)) {
                is ApiResult.Success -> {
                    _sensusList.value = result.data
                    _currentPage.value = page
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }
            _isLoading.value = false
        }
    }

    //load by id
    fun loadSensusById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repositori.getSensusById(id)) {
                is ApiResult.Success -> {
                    _selectedSensus.value = result.data
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }
            _isLoading.value = false
        }
    }

    //form kelola
    fun updateLokoId(value: Int) {
        _sensusFormState.update { it.copy(
            sensusDetail = it.sensusDetail.copy(loko_id = value)
        )}
        validateForm()
    }

    fun updateKaId(value: Int) {
        _sensusFormState.update { it.copy(
            sensusDetail = it.sensusDetail.copy(ka_id = value)
        )}
        validateForm()
    }

    private fun validateForm() {
        val isValid = _sensusFormState.value.sensusDetail.isValid()
        _sensusFormState.update { it.copy(isEntryValid = isValid) }
    }

    //create
    fun createSensus(token: String) {
        val formData = _sensusFormState.value.sensusDetail
        if (!formData.isValid()) {
            _sensusFormState.update { it.copy(
                errorMessage = "Lokomotif dan Kereta harus dipilih"
            )}
            return
        }
        viewModelScope.launch {
            _sensusFormState.update { it.copy(isLoading = true) }
            val request = formData.toCreateRequest()
            when (val result = repositori.createSensus(token, request)) {
                is ApiResult.Success -> {
                    _sensusFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = null
                    )}
                    resetForm()
                    loadAllSensus()
                }
                is ApiResult.Error -> {
                    _sensusFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )}
                }
                is ApiResult.Loading -> { }
            }
        }
    }

    //update
    fun updateSensus(token: String, id: Int) {
        val formData = _sensusFormState.value.sensusDetail

        if (!formData.isValid()) {
            _sensusFormState.update { it.copy(
                errorMessage = "Lokomotif dan Kereta harus dipilih"
            )}
            return
        }

        viewModelScope.launch {
            _sensusFormState.update { it.copy(isLoading = true) }

            val request = formData.toCreateRequest()

            when (val result = repositori.updateSensus(token, id, request)) {
                is ApiResult.Success -> {
                    _sensusFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = null
                    )}

                    resetForm()
                    loadAllSensus()
                }
                is ApiResult.Error -> {
                    _sensusFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )}
                }
                is ApiResult.Loading -> { }
            }
        }
    }
}