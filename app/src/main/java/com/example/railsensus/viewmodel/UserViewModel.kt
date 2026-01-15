package com.example.railsensus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.railsensus.modeldata.StatistikUser
import com.example.railsensus.modeldata.UserManagement
import com.example.railsensus.repositori.ApiResult
import com.example.railsensus.repositori.RepositoriRailSensus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repositori: RepositoriRailSensus
): ViewModel() {
    // list users
    private val _userList = MutableStateFlow<List<UserManagement>>(emptyList())
    val userList: StateFlow<List<UserManagement>> = _userList.asStateFlow()

    // selected user for detail
    private val _selectedUser = MutableStateFlow<UserManagement?>(null)
    val selectedUser: StateFlow<UserManagement?> = _selectedUser.asStateFlow()

    // statistik
    private val _statistik = MutableStateFlow<StatistikUser?>(null)
    val statistik: StateFlow<StatistikUser?> = _statistik.asStateFlow()

    // loading & Error
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    //load all user
    fun loadAllUsers(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = repositori.getAllUsers(token)) {
                is ApiResult.Success -> {
                    _userList.value = result.data
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }

            _isLoading.value = false
        }
    }

    //user by id
    fun loadUserById(token: String, id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repositori.getUserById(token, id)) {
                is ApiResult.Success -> {
                    _selectedUser.value = result.data
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
    fun loadStatistik(token: String) {
        viewModelScope.launch {
            when (val result = repositori.getUserStatistik(token)) {
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

    //delete
    fun deleteUser(token: String, id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repositori.deleteUser(token, id)) {
                is ApiResult.Success -> {
                    loadAllUsers(token)
                }
                is ApiResult.Error -> {
                    _errorMessage.value = result.message
                }
                is ApiResult.Loading -> { }
            }
            _isLoading.value = false
        }
    }
}