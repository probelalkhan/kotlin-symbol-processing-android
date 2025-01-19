package dev.belalkhan.kspsampleapp.ui.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SignupState>(SignupState())
    val uiState: StateFlow<SignupState> = _uiState

    fun onEvent(uiEvent: SignupUiEvent) {
        when (uiEvent) {
            is SignupUiEvent.FullNameChanged -> updateState { it.copy(fullName = uiEvent.fullName) }
            is SignupUiEvent.EmailChanged -> updateState { it.copy(email = uiEvent.email) }
            is SignupUiEvent.PasswordChanged -> updateState { it.copy(password = uiEvent.password) }
            is SignupUiEvent.ConfirmPasswordChanged -> updateState { it.copy(passwordConfirm = uiEvent.password) }
            SignupUiEvent.Signup -> { areInputsValid() }
            SignupUiEvent.Login -> {}
        }
    }

    private fun updateState(update: (SignupState) -> SignupState) {
        _uiState.value = update(_uiState.value)
        areInputsValid()
    }

    private fun areInputsValid(): Boolean {
        return true
    }
}
