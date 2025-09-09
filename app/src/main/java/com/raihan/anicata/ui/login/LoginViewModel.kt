package com.raihan.anicata.ui.login

import androidx.lifecycle.ViewModel
import com.raihan.anicata.data.model.LoginResult
import com.raihan.anicata.data.model.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: LoginResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _state.update { LoginState() }
    }

}