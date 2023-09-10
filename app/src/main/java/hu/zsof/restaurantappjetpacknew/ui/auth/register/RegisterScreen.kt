package hu.zsof.restaurantappjetpacknew.ui.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.ui.common.button.LoginButton
import hu.zsof.restaurantappjetpacknew.ui.common.field.NormalTextField
import hu.zsof.restaurantappjetpacknew.ui.common.field.PasswordTextField
import hu.zsof.restaurantappjetpacknew.ui.common.showToast
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun RegisterScreen_Preview() {
    RegisterScreen(
        onLoginClick = { },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),

                )
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextField(
                    value = viewModel.email.value,
                    label = stringResource(id = R.string.email_address),
                    onValueChange = { newValue ->
                        viewModel.email.value = newValue
                        viewModel.validateEmail()
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
                    onDone = { },
                    placeholder = stringResource(id = R.string.email_address),
                )
                Spacer(modifier = Modifier.height(10.dp))
                NormalTextField(
                    value = viewModel.userName.value,
                    label = stringResource(id = R.string.user_name),
                    onValueChange = { newValue ->
                        viewModel.userName.value = newValue
                        viewModel.validateUserName()
                    },
                    isError = viewModel.isUserNameError.value,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    trailingIcon = { },
                    onDone = { },
                    placeholder = stringResource(id = R.string.user_name),
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextField(
                    value = viewModel.password.value,
                    label = stringResource(id = R.string.password_text),
                    onValueChange = { newValue ->
                        viewModel.password.value = newValue
                        viewModel.validatePassword()
                    },
                    isError = viewModel.isPasswordError.value,
                    isVisible = viewModel.isPasswordVisible.value,
                    onVisibilityChanged = {
                        viewModel.isPasswordVisible.value = !viewModel.isPasswordVisible.value
                    },
                    onDone = { },
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = viewModel.isOwner.value,
                        onCheckedChange = { checkedNew ->
                            viewModel.isOwner.value = checkedNew
                        },
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.register_as_owner),
                        style = TextStyle(fontSize = 14.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                LoginButton(
                    onClick = {
                        scope.launch {
                            val response = viewModel.register()
                            if (response.isSuccess) {
                                showToast(context, response.successMessage)
                            }
                        }
                        keyboardController?.hide()
                    },
                    text = stringResource(R.string.register),
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = stringResource(id = R.string.have_account),
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 16.sp,
                    )
                    Text(
                        text = stringResource(id = R.string.log_in),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 4.dp)
                            .clickable(onClick = onLoginClick),
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                    )
                }
            }
        },
    )
}
