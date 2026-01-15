package com.example.railsensus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.railsensus.modeldata.UILoginState
import com.example.railsensus.modeldata.UIRegisterState
import com.example.railsensus.modeldata.UserProfile
import com.example.railsensus.modeldata.isValid
import com.example.railsensus.repositori.ApiResult
import com.example.railsensus.repositori.RepositoriRailSensus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    fun updateLoginPassword(password: String) {
        _loginState.update { it.copy(loginData = it.loginData.copy(password = password)) }
        validateLogin()
    }

    private suspend fun loadUserProfile(token: String) {
        when (val result = repository.getProfile(token)) {
            is ApiResult.Success -> {
                _currentUser.value = result.data
            }
            is ApiResult.Error -> {
            }
            is ApiResult.Loading -> {}
        }
    }


    fun login() {
        val loginData = _loginState.value.loginData
        if (!loginData.isValid()) {
            _loginState.update { it.copy(errorMessage = "Email dan password tidak boleh kosong")}
            return
        }
        viewModelScope.launch {
            _loginState.update { it.copy(
                isLoading = true,
                errorMessage = null
            ) }

            when (val result = repository.login(loginData.email, loginData.password)) {
                is ApiResult.Success -> {
                    _currentToken.value = result.data.accessToken
                    loadUserProfile(result.data.accessToken)
                    _loginState.update { it.copy(
                        isLoading = false,
                        errorMessage = null
                    )}
                }
                is ApiResult.Error -> {
                    _loginState.update { it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    ) }
                }
                is ApiResult.Loading -> {}
            }
        }
    }

    fun clearLoginError() {
        _loginState.update { it.copy(errorMessage = null) }
    }

    //Register
    private fun validateRegister() {
        val isValid = _registerState.value.registerData.isValid()
        _registerState.update { it.copy(isEntryValid = isValid) }
    }

    fun updateRegisterUsername(username: String) {
        _registerState.update { it.copy(registerData = it.registerData.copy(username = username)) }
        validateRegister()
    }

    fun updateRegisterEmail(email: String) {
        _registerState.update { it.copy(
            registerData = it.registerData.copy(email = email)
        )}
        validateRegister()
    }

    fun updateRegisterPassword(password: String) {
        _registerState.update { it.copy(
            registerData = it.registerData.copy(password = password)
        )}
        validateRegister()
    }

    fun updateRegisterConfirmPassword(confirmPassword: String) {
        _registerState.update {
            it.copy(
                registerData = it.registerData.copy(confirmPassword = confirmPassword)
            )
        }
        validateRegister()
    }

    fun register() {
        val registerData = _registerState.value.registerData
        if (!registerData.isValid()) {
            _registerState.update { it.copy(
                errorMessage = "Semua field harus diisi dan password harus sama"
            )}
            return
        }

        viewModelScope.launch {
            _registerState.update { it.copy(
                isLoading = true,
                errorMessage = null
            )}

            when (val result = repository.register(
                username = registerData.username,
                email = registerData.email,
                password = registerData.password
            )) {
                is ApiResult.Success -> {
                    _registerState.update { it.copy(
                        isLoading = false,
                        errorMessage = null
                    ) }
                }
                is ApiResult.Error -> {
                    _registerState.update { it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )}
                }
                is ApiResult.Loading -> { }
            }
        }
    }
}