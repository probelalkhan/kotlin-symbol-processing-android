package dev.belalkhan.kspsampleapp.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.belalkhan.kspsampleapp.R
import dev.belalkhan.kspsampleapp.ui.AppPreview
import dev.belalkhan.kspsampleapp.ui.components.AppTextField
import dev.belalkhan.kspsampleapp.ui.theme.AppTheme

@Composable
fun Signup(modifier: Modifier = Modifier, viewModel: SignupViewModel) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp, bottom = 32.dp),
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "mini tales",
        )

        AppTextField(
            value = uiState.value.fullName,
            error = uiState.value.fullNameError,
            label = R.string.full_name,
            hint = "John Doe",
            leadingIcon = Icons.Filled.Person,
            onValueChanged = { viewModel.onEvent(SignupUiEvent.FullNameChanged(it)) },
            imeAction = ImeAction.Next,
        )

        AppTextField(
            value = uiState.value.email,
            error = uiState.value.emailError,
            label = R.string.email,
            hint = "yourname@domain.com",
            leadingIcon = Icons.Filled.Email,
            onValueChanged = { viewModel.onEvent(SignupUiEvent.EmailChanged(it)) },
            imeAction = ImeAction.Next,
        )

        AppTextField(
            value = uiState.value.password,
            error = uiState.value.passwordError,
            label = R.string.password,
            hint = "your password",
            leadingIcon = Icons.Filled.Lock,
            isPasswordField = true,
            onValueChanged = { viewModel.onEvent(SignupUiEvent.PasswordChanged(it)) },
            imeAction = ImeAction.Next,
        )

        AppTextField(
            value = uiState.value.passwordConfirm,
            error = uiState.value.passwordConfirmError,
            label = R.string.confirm_password,
            hint = "same password again",
            leadingIcon = Icons.Filled.Lock,
            isPasswordField = true,
            onValueChanged = { viewModel.onEvent(SignupUiEvent.ConfirmPasswordChanged(it)) },
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.onEvent(SignupUiEvent.Signup) },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "login",
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .clickable { },
            text = stringResource(R.string.already_have_account),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            text = stringResource(R.string.agree_to_terms),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@AppPreview
@Composable
private fun PreviewSignupScreen() {
    AppTheme {
        Surface {
            Signup(viewModel = SignupViewModel())
        }
    }
}
