package com.example.railsensus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.railsensus.modeldata.Lokomotif
import com.example.railsensus.modeldata.StatistikLoko
import com.example.railsensus.modeldata.UILokomotifState
import com.example.railsensus.modeldata.isValid
import com.example.railsensus.modeldata.toCreateRequest
import com.example.railsensus.modeldata.toUILokomotifState
import com.example.railsensus.repositori.ApiResult
import com.example.railsensus.repositori.RepositoriRailSensus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LokoViewModel(
    private val repositori: RepositoriRailSensus
) : ViewModel() {
    //State

    //List Loko
    private val _lokoList = MutableStateFlow<List<Lokomotif>>(emptyList())
    val lokoList: StateFlow<List<Lokomotif>> = _lokoList.asStateFlow()

    //create/update
    private val _lokoFormState = MutableStateFlow(UILokomotifState())
    val lokoFormState: StateFlow<UILokomotifState> = _lokoFormState.asStateFlow()

    //select
    private val _selectedLoko = MutableStateFlow<Lokomotif?>(null)
    val selectedLoko: StateFlow<Lokomotif?> = _selectedLoko.asStateFlow()

    //statistik
    private val _statistik = MutableStateFlow<StatistikLoko?>(null)
    val statistik: StateFlow<StatistikLoko?> = _statistik.asStateFlow()

    //loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    //pesan error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    //load all
    fun loadAllLoko() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = repositori.getAllLoko()) {
                is ApiResult.Success -> {
                    _lokoList.value = result.data
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
    fun loadLokoById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            when (val result = repositori.getLokoById(id)) {
                is ApiResult.Success -> {
                    _selectedLoko.value = result.data
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
    fun searchLoko(query: String) {
        if (query.isBlank()) {
            loadAllLoko()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true

            when (val result = repositori.searchLoko(query)) {
                is ApiResult.Success -> {
                    _lokoList.value = result.data
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
            when (val result = repositori.getLokoStatistik()) {
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

    //form kelola
    private fun validateForm() {
        val isValid = _lokoFormState.value.lokomotif.isValid()
        _lokoFormState.update { it.copy(isEntryValid = isValid) }
    }

    fun updateNomorSeri(value: String) {
        _lokoFormState.update { it.copy(lokomotif = it.lokomotif.copy(nomor_seri = value))  }
        validateForm()
    }

    fun updateDipoInduk(value: String) {
        _lokoFormState.update { it.copy(lokomotif = it.lokomotif.copy(dipo_induk = value)) }
        validateForm()
    }

    fun updateStatus(value: String) {
        _lokoFormState.update { it.copy(lokomotif = it.lokomotif.copy(status = value)) }
        validateForm()
    }

    //create
    fun createLoko(token: String) {
        val formData = _lokoFormState.value.lokomotif

        if (!formData.isValid()) {
            _lokoFormState.update { it.copy(errorMessage = "Nomor Seri dan Dipo Induk harus diisi") }
            return
        }

        viewModelScope.launch {
            _lokoFormState.update { it.copy(isLoading = true) }

            val request = formData.toCreateRequest()
            when (val result = repositori.createLoko(token, request)) {
                is ApiResult.Success -> {
                    _lokoFormState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                    resetForm()
                    loadAllLoko()
                }
                is ApiResult.Error -> {
                    _lokoFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    ) }
                }
                is ApiResult.Loading -> { }
            }
        }
    }

    //update
    fun updateLoko(token: String, id: Int) {
        val formData = _lokoFormState.value.lokomotif

        if (!formData.isValid()) {
            _lokoFormState.update { it.copy(
                errorMessage = "Nomor Seri dan Dipo Induk harus diisi"
            )}
            return
        }

        viewModelScope.launch {
            _lokoFormState.update { it.copy(isLoading = true) }
            val request = formData.toCreateRequest()
            when (val result = repositori.updateLoko(token, id, request)) {
                is ApiResult.Success -> {
                    _lokoFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = null
                    )}

                    resetForm()
                    loadAllLoko()
                    loadLokoById(id)  // Reload detail to refresh the view
                }
                is ApiResult.Error -> {
                    _lokoFormState.update { it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )}
                }
                is ApiResult.Loading -> { }
            }
        }
    }

    //delete
    fun deleteLoko(token: String, id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repositori.deleteLoko(token, id)) {
                is ApiResult.Success -> {
                    loadAllLoko()
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }
            _isLoading.value = false
        }
    }

    //helperrrrr
    fun setSelectedLoko(loko: Lokomotif?) {
        _selectedLoko.value = loko
        if (loko != null) {
            _lokoFormState.value = loko.toUILokomotifState(isEntryValid = true)
        }
    }

    fun resetForm() {
        _lokoFormState.value = UILokomotifState()
        _selectedLoko.value = null
    }

    fun clearError() {
        _errorMessage.value = null
        _lokoFormState.update { it.copy(errorMessage = null) }
    }
}


























