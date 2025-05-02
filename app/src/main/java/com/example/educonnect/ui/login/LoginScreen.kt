package com.example.educonnect.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.educonnect.R
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.components.CustomIconButton
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.signup.CustomTextField
import com.example.educonnect.ui.theme.EduConnectTheme

object LoginDestination : NavigationDestination {
    override val route = "login"
    override val titleRes = R.string.signin
}

@Composable
fun LoginScreen(
    navigateToHomeScreen : () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    viewModel: LoginViewModel = viewModel(
        factory = EduViewModelProvider.Factory
    ),

) {
    val uiState = viewModel.loginUiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isVisible by remember {
        mutableStateOf(false)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }

    LoginBody(
        email = email,
        password = password,
        isVisible = isVisible,
        onEmailChange = {
            email = it
        },
        onPasswordChange = {
            password = it
        },
        onVisibilityChange = {
            isVisible = !isVisible
        },
        onResetPassword = {
            showDialog = true
        },
        navigateToSignUpScreen = navigateToSignUpScreen,
        onSignIn = {
            viewModel.loginUser(
                email = email,
                password = password
            )
            if (uiState.value == LoginUiState.Success) {
                navigateToHomeScreen()
            }
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Quên mật khẩu?") },
            text = {
                Column {
                    Text("Nhập email của bạn để đặt lại mật khẩu.")
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.forgotPassword(email)
                    showDialog = false
                }) {
                    Text("Xác nhận")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}

@Composable
private fun LoginBody(
    email : String,
    password : String,
    isVisible : Boolean,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,
    onVisibilityChange : () -> Unit,
    onResetPassword : () -> Unit,
    onSignIn : () -> Unit,
    navigateToSignUpScreen : () -> Unit
) {
    val isFormValid = email.isNotBlank() &&
            password.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.signin),
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            stringResource(R.string.welcome),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray,
            modifier = Modifier.padding(
                vertical = 20.dp,
                horizontal = 45.dp
            ),
            textAlign = TextAlign.Center
        )

        CustomTextField(
            title = R.string.email,
            value = email,
            onValueChange = onEmailChange,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    stringResource(R.string.john_wick),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray,
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = stringResource(R.string.email),
                    tint = Color.Black.copy(
                        alpha = 0.7f
                    )
                )
            },
            trailingIcon = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 24.dp,
                )
        )

        CustomTextField(
            title = R.string.password,
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = if (isVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            placeholder = {
                Text(
                    stringResource(R.string.password),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Rounded.Lock,
                    contentDescription = stringResource(R.string.password)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = onVisibilityChange
                ) {
                    Icon(
                        painter = if (isVisible) {
                            painterResource(id = R.drawable.eye_svgrepo_com)
                        } else {
                            painterResource(id = R.drawable.eye_invisible_svgrepo_com)
                        },
                        contentDescription = "Visibility",
                        modifier = Modifier.size(25.dp)
                    )
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 24.dp,
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                colors = ButtonColors(
                    disabledContentColor = Color(0xFF0961F5),
                    contentColor = Color(0xFF0961F5),
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                onClick = onResetPassword
            ) {
                Text(
                    stringResource(id = R.string.forgot),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp
                ),
            enabled = isFormValid,
            colors = ButtonColors(
                containerColor = Color(0xFF0961F5),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFF0961F5),
                disabledContentColor = Color.White
            ),
            onClick = onSignIn
        ) {
            Text(
                stringResource(R.string.signin),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    vertical = 8.dp
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 24.dp,
                    horizontal = 40.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color.Black.copy(
                    alpha = 0.1f
                )
            )
            Text(
                stringResource(id = R.string.orr),
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp,
                color = Color.Gray
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color.Black.copy(
                    alpha = 0.1f
                )
            )
        }

        Row(
            modifier = Modifier
                .padding(
                    top = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black.copy(
                            alpha = 0.1f
                        ),
                        shape = MaterialTheme.shapes.large
                    ),
                contentAlignment = Alignment.Center
            ) {
                CustomIconButton(
                    icon = R.drawable.google,
                    contentDescription = "Google Login",
                    onClick = { /*TODO*/ },
                    tint = Color.Unspecified
                )
            }

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black.copy(
                            alpha = 0.1f
                        ),
                        shape = MaterialTheme.shapes.large
                    ),
                contentAlignment = Alignment.Center
            ) {
                CustomIconButton(
                    icon = R.drawable.facebook_2_logo_svgrepo_com,
                    contentDescription = "Facebook Login",
                    onClick = { /*TODO*/ },
                    tint = Color.Unspecified
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(
                    vertical = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(id = R.string.dont),
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp,
                color = Color.Black.copy(
                    alpha = 0.7f
                )
            )
            TextButton(
                colors = ButtonColors(
                    disabledContentColor = Color(0xFF0961F5),
                    contentColor = Color(0xFF0961F5),
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                onClick = navigateToSignUpScreen
            ) {
                Text(
                    stringResource(id = R.string.signup),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 12.sp,
                )
            }
        }
    }
}
