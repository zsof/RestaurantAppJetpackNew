package hu.zsof.restaurantappjetpacknew.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.ui.common.LoginButton
import hu.zsof.restaurantappjetpacknew.ui.common.NormalTextField
import hu.zsof.restaurantappjetpacknew.ui.common.PasswordTextField

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun RegisterScreen_Preview() {
    RegisterScreen(
        onLoginClick = { },
        onRegisterClick = { },
    )
}

@ExperimentalMaterial3Api
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onRegisterClick: (String) -> Unit,
) {
    var emailValue by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }

    var userNameValue by remember { mutableStateOf("") }

    var nickNameValue by remember { mutableStateOf("") }

    var passwordValue by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

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
                value = emailValue,
                label = stringResource(id = R.string.email_address),
                onValueChange = { newValue ->
                    emailValue = newValue
                    isEmailError = false
                },
                isError = isEmailError,
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
                value = userNameValue,
                label = stringResource(id = R.string.user_name),
                onValueChange = { newValue ->
                    userNameValue = newValue
                    isEmailError = false
                },
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
            NormalTextField(
                value = nickNameValue,
                label = stringResource(id = R.string.nickname),
                onValueChange = { newValue ->
                    nickNameValue = newValue
                    isEmailError = false
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person2,
                        contentDescription = null,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                trailingIcon = { },
                onDone = { },
                placeholder = stringResource(id = R.string.nickname),
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordTextField(
                value = passwordValue,
                label = stringResource(id = R.string.password_text),
                onValueChange = { newValue ->
                    passwordValue = newValue
                    isPasswordError = false
                },
                isError = isPasswordError,
                isVisible = isPasswordVisible,
                onVisibilityChanged = { isPasswordVisible = !isPasswordVisible },
                onDone = { },
            )
            Spacer(modifier = Modifier.height(10.dp))
            LoginButton(
                onClick = {
                    if (emailValue.isEmpty()) {
                        isEmailError = true
                    } else if (passwordValue.isEmpty()) {
                        isPasswordError = true
                    } else {
                        onRegisterClick(emailValue)
                    }
                },
                text = stringResource(R.string.register),
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
                    imageVector = Icons.Outlined.Games,
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
                    text = stringResource(id = R.string.have_account),
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    fontSize = 16.sp,
                )
                Text(
                    text = stringResource(id = R.string.log_in),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 4.dp)
                        .clickable(onClick = onLoginClick),
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                )
            }
        }
    }
}
