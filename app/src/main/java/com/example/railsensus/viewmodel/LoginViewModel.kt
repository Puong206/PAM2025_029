package com.example.railsensus.viewmodel

import androidx.lifecycle.ViewModel
import com.example.railsensus.modeldata.UILoginState
import com.example.railsensus.modeldata.UIRegisterState
import com.example.railsensus.modeldata.UserProfile
import com.example.railsensus.modeldata.isValid
import com.example.railsensus.repositori.RepositoriRailSensus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel (
    private val repository: RepositoriRailSensus
) : ViewModel() {

    //Login State
    private val _loginState = MutableStateFlow(UILoginState())
    val loginState: StateFlow<UILoginState> = _loginState.asStateFlow()

    //Register State
    private val _registerState = MutableStateFlow(UIRegisterState())
    val registerState: StateFlow<UIRegisterState> = _registerState.asStateFlow()

    //Penyimpanan Token
    private val _currentToken = MutableStateFlow<String?>(null)
    val currentToken: StateFlow<String?> = _currentToken.asStateFlow()

    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    //Login Function
    private fun validateLogin() {
        val isValid = _loginState.value.loginData.isValid()
        _loginState.update { it.copy(isEntryValid = isValid) }
    }

    fun updateLoginEmail(email: String) {
        _loginState.update { it.copy(loginData = it.loginData.copy(email = email)) }
        validateLogin()
    }

}