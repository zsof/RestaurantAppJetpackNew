package hu.zsof.restaurantappjetpacknew.ui.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.button.LoginButton
import hu.zsof.restaurantappjetpacknew.ui.common.field.NormalTextField
import hu.zsof.restaurantappjetpacknew.ui.common.field.PasswordTextField
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.extension.showToast
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: (String) -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier
            .fillMaxSize(),
        content =
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.welcome_text),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontStyle = FontStyle.Italic,
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),

                    )
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextField(
                    value = viewModel.email.value,
                    label = stringResource(id = R.string.email_address),
                    onValueChange = { newValue ->
                        viewModel.email.value = newValue
                    },
                    isError = viewModel.isEmailError.value,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    trailingIcon = { },
                    onDone = { viewModel.validateEmail() },
                    placeholder = stringResource(id = R.string.email_address),
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextField(
                    value = viewModel.password.value,
                    label = stringResource(id = R.string.password_text),
                    onValueChange = { newValue ->
                        viewModel.password.value = newValue
                    },
                    isError = viewModel.isPasswordError.value,
                    isVisible = viewModel.isPasswordVisible.value,
                    onVisibilityChanged = {
                        viewModel.isPasswordVisible.value = !viewModel.isPasswordVisible.value
                    },
                    onDone = {
                        viewModel.validatePassword()
                        keyboardController?.hide()
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                LoginButton(
                    onClick = {
                        scope.launch {
                            val response = viewModel.login()

                            if (response.isSuccess) {
                                showToast(
                                    context = context,
                                    message = (response.successMessage),
                                )
                                onLoginClick(viewModel.email.value)
                                when (response.user.userType) {
                                    Constants.ROLE_ADMIN -> {
                                        viewModel.setAppPreference(
                                            Constants.Prefs.USER_TYPE,
                                            Constants.ROLE_ADMIN
                                        )
                                    }

                                    Constants.ROLE_USER -> {
                                        viewModel.setAppPreference(
                                            Constants.Prefs.USER_TYPE,
                                            Constants.ROLE_USER
                                        )
                                    }

                                    Constants.ROLE_OWNER -> {
                                        viewModel.setAppPreference(
                                            Constants.Prefs.USER_TYPE,
                                            Constants.ROLE_OWNER
                                        )
                                    }
                                }
                                viewModel.setAppPreference(Constants.Prefs.USER_LOGGED, true)
                                AppState.loggedUser.value = response.user
                            } else {
                                showToast(context, response.error)
                            }
                        }
                        keyboardController?.hide()
                    },

                    text = stringResource(R.string.log_in),
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = stringResource(id = R.string.no_account),
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,

                        )
                    Text(
                        text = stringResource(id = R.string.register),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 4.dp)
                            .clickable(onClick = onRegisterClick, enabled = true),
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                    )
                }
            }
        },
    )
}
