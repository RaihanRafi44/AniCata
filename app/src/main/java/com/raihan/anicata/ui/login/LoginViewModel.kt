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
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onSignInStarted() {
        _state.update {
            it.copy(
                isLoading = true,
                signInError = null,
                loadingMessage = "AUTHENTICATING..."
            )
        }
    }

    fun onSignInCancelled() {
        _state.update {
            it.copy(
                isLoading = false,
                loadingMessage = "AUTHENTICATING..."
            )
        }
    }

    fun onErrorDismissed() {
        _state.update {
            it.copy(signInError = null)
        }
    }

    fun onSignInResult(result: LoginResult) {
        val userData = result.data
        if (userData != null) {
            _state.update {
                it.copy(
                    isLoading = true,
                    signInError = null,
                    loadingMessage = "SYNCING ARCHIVE...",
                    username = userData.username
                )
            }
            viewModelScope.launch {
                userRepository.syncUser(userData).collect { _ ->
                    _state.update {
                        it.copy(
                            isSignInSuccessful = true,
                            isLoading = false,
                            loadingMessage = "AUTHENTICATING...",
                            username = userData.username
                        )
                    }
                }
            }
        } else {
            _state.update {
                it.copy(
                    isSignInSuccessful = false,
                    signInError = result.errorMessage ?: "Authentication failed. Please try again.",
                    isLoading = false,
                    loadingMessage = "AUTHENTICATING...",
                    username = null
                )
            }
        }
    }

    fun resetState() {
        _state.update { LoginState() }
    }
}
