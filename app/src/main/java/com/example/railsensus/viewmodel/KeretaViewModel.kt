package com.example.railsensus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.railsensus.modeldata.*
import com.example.railsensus.repositori.ApiResult
import com.example.railsensus.repositori.RepositoriRailSensus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    //load all
    fun loadAllKereta() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = repositori.getAllKereta()) {
                is ApiResult.Success -> {
                    _keretaList.value = result.data
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }

            _isLoading.value = false
        }
    }

    //loadbyid
    fun loadKeretaById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repositori.getKeretaById(id)) {
                is ApiResult.Success -> {
                    _selectedKereta.value = result.data
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }
            _isLoading.value = false
        }
    }

    //search
    fun searchKereta(query: String) {
        if (query.isBlank()) {
            loadAllKereta()
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repositori.searchKereta(query)) {
                is ApiResult.Success -> {
                    _keretaList.value = result.data
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }
            _isLoading.value = false
        }
    }

    //statistik
    fun loadStatistik() {
        viewModelScope.launch {
            when (val result = repositori.getKeretaStatistik()) {
                is ApiResult.Success -> {
                    _statistik.value = result.data
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }
        }
    }

    //kelola form
    private fun validateForm() {
        val isValid = _keretaFormState.value.keretaDetail.isValid()
        _keretaFormState.update { it.copy(isEntryValid = isValid) }
    }

    fun updateNamaKa(value: String) {
        _keretaFormState.update { it.copy(
            keretaDetail = it.keretaDetail.copy(nama_ka = value)
        )}
        validateForm()
    }

    fun updateNomorKa(value: String) {
        _keretaFormState.update { it.copy(
            keretaDetail = it.keretaDetail.copy(nomor_ka = value)
        )}
        validateForm()
    }
    
    //create
    fun createKereta(token: String) {
        val formData = _keretaFormState.value.keretaDetail

        if (!formData.isValid()) {
            _keretaFormState.update { it.copy(
                errorMessage = "Nama kereta harus diisi"
            )}
            return
        }

        viewModelScope.launch {
            _keretaFormState.update { it.copy(isLoading = true) }

            val request = formData.toCreateRequest()

            when (val result = repositori.createKereta(token, request)) {
                is ApiResult.Success -> {
                    _keretaFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = null
                    )}

                    resetForm()
                    loadAllKereta()
                }
                is ApiResult.Error -> {
                    _keretaFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )}
                }
                is ApiResult.Loading -> { }
            }
        }
    }

    //update
    fun updateKereta(token: String, id: Int) {
        val formData = _keretaFormState.value.keretaDetail

        if (!formData.isValid()) {
            _keretaFormState.update { it.copy(
                errorMessage = "Nama kereta harus diisi"
            )}
            return
        }

        viewModelScope.launch {
            _keretaFormState.update { it.copy(isLoading = true) }

            val request = formData.toCreateRequest()

            when (val result = repositori.updateKereta(token, id, request)) {
                is ApiResult.Success -> {
                    _keretaFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = null
                    )}

                    resetForm()
                    loadAllKereta()
                }
                is ApiResult.Error -> {
                    _keretaFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )}
                }
                is ApiResult.Loading -> { }
            }
        }
    }

    //delete
    fun deleteKereta(token: String, id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repositori.deleteKereta(token, id)) {
                is ApiResult.Success -> {
                    loadAllKereta()
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }
            _isLoading.value = false
        }
    }

    //helper
    fun setSelectedKereta(kereta: Kereta?) {
        _selectedKereta.value = kereta

        if (kereta != null) {
            _keretaFormState.value = kereta.toUIKeretaState(isEntryValid = true)
        }
    }

    fun resetForm() {
        _keretaFormState.value = UIKeretaState()
        _selectedKereta.value = null
    }

    fun clearError() {
        _errorMessage.value = null
        _keretaFormState.update { it.copy(errorMessage = null) }
    }
}