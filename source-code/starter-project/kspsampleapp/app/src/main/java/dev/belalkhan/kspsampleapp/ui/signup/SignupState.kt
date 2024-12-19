package dev.belalkhan.kspsampleapp.ui.signup

import androidx.annotation.StringRes

data class SignupState(
    val fullName: String = "",
    @StringRes val fullNameError: Int? = null,

    val email: String = "",
    @StringRes val emailError: Int? = null,

    val password: String = "",
    @StringRes val passwordError: Int? = null,

    val passwordConfirm: String = "",
    @StringRes val passwordConfirmError: Int? = null,
)
