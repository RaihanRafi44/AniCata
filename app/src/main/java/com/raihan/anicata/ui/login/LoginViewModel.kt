package com.raihan.anicata.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.auth.LoginResult
import com.raihan.anicata.data.model.auth.LoginState
import com.raihan.anicata.data.repository.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: LoginResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
            )
        }

        val userData = result.data
        if (userData != null) {
            viewModelScope.launch {
                userRepository.syncUser(userData).collect { syncResult ->
                    println("Sync Result: $syncResult")
                }
            }
        }
    }

    fun resetState() {
        _state.update { LoginState() }
    }

}