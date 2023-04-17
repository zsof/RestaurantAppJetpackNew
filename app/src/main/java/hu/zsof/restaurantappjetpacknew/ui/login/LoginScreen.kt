package hu.zsof.restaurantappjetpacknew.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.common.LoginButton
import hu.zsof.restaurantappjetpacknew.ui.common.NormalTextField
import hu.zsof.restaurantappjetpacknew.ui.common.PasswordTextField
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_ADMIN
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_USER
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: (String) -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter,
    ) {
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
            LoginButton(
                onClick = {
                    scope.launch {
                        val response = viewModel.login()
                        if (response.isSuccess) {
                            onLoginClick(viewModel.email.value)
                            if (response.user.userType == ROLE_ADMIN) {
                                LocalDataStateService.userType.postValue(ROLE_ADMIN)
                            } else if (response.user.userType == ROLE_USER) {
                                LocalDataStateService.userType.postValue(ROLE_USER)
                            }
                        } else {
                            println("login failed ${response.error}")
                        }
                    }
                },
                text = stringResource(R.string.log_in),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.padding(vertical = 16.dp)) {
                Spacer(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray),
                )
                Text(
                    text = stringResource(id = R.string.or_use),
                    color = Color.LightGray,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(
                    topEnd = 20.dp,
                    topStart = 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp,
                ),

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.googleg_standard_color_18),
                    contentDescription = null,
                )
                Text(
                    text = stringResource(id = R.string.sign_in_with_google),
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = stringResource(id = R.string.no_account),
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    fontSize = 16.sp,
                )
                Text(
                    text = stringResource(id = R.string.register),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 4.dp)
                        .clickable(onClick = onRegisterClick, enabled = true),
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                )
            }
        }
    }
}

/*@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun LoginScreen_Preview() {
    LoginScreen(
        onLoginClick = { },
        onRegisterClick = { },
        navController = {}
    )
}*/
