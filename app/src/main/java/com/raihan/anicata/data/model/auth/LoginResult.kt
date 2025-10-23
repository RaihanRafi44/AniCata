package com.raihan.anicata.data.model.auth

data class LoginResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)

data class LoginState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
