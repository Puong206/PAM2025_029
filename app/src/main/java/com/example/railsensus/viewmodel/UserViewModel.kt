package com.example.railsensus.viewmodel

import androidx.lifecycle.ViewModel
import com.example.railsensus.modeldata.StatistikUser
import com.example.railsensus.modeldata.UserManagement
import com.example.railsensus.repositori.RepositoriRailSensus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
}