package com.example.educonnect.ui.signup

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.components.CustomIconButton
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.theme.EduConnectTheme
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sin

object SignUpDestination : NavigationDestination {
    override val route = "sign_up"
    override val titleRes = R.string.signup
}

@Composable
fun SignupScreen(
    navigateToInformationScreen : (String) -> Unit,
    viewModel: SignupViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    ),
//    navigateToRoleSelectionScreen : () -> Unit
) {
    val uiState = viewModel._registerUiState
//    val currentUid = FirebaseAuth.getInstance().currentUser!!.uid

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isVisible by remember {
        mutableStateOf(false)
    }
    var isChecked by remember {
        mutableStateOf(false)
    }
    var selectedRole by remember {
        mutableStateOf("")
    }

    SignupBody(
        email = email,
        password = password,
        isVisible = isVisible,
        isChecked = isChecked,
        selectedRole = selectedRole,
        onSelectedRole = {
            selectedRole = it
        },
        onEmailChange = {
            email = it
        },
        onPasswordChange = {
            password = it
        },
        onCheckChange = {
            isChecked = !isChecked
        },
        onVisibilityChange = {
            isVisible = !isVisible
        },
        onSignUp = {
            viewModel.registerUser(email, password, selectedRole)
            Log.d("NAV_DEBUG", "Navigating with UID: ${uiState.uid}")
            navigateToInformationScreen(uiState.uid)
        }
    )
}

@Composable
fun SignupBody(
    email : String,
    password : String,
    isVisible : Boolean,
    isChecked : Boolean,
    selectedRole: String,
    onSelectedRole : (String) -> Unit,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,
    onCheckChange : (Boolean) -> Unit,
    onVisibilityChange : () -> Unit,
    onSignUp : () -> Unit
) {
    val isFormValid = email.isNotBlank() &&
            password.isNotBlank() &&
            isChecked &&
            selectedRole.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.create),
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            stringResource(R.string.fill_infor),
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
                    stringResource(R.string.example_gmail),
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

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                "Bạn là:",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black.copy(
                    alpha = 0.7f
                ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (role in Role.entries) {
                    RoleSelection(
                        role = role,
                        isSelected = selectedRole == role.displayName,
                        onRoleSelected = {
                            onSelectedRole(role.displayName)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
//                RoleSelection(
//                    role = Role.TEACHER,
//                    isSelected = selectedRole == Role.TEACHER.displayName,
//                    onRoleSelected = onRoleSelected,
//                    modifier = Modifier.weight(1f)
//                )
//                RoleSelection(
//                    role = Role.STUDENT,
//                    isSelected = selectedRole == Role.STUDENT.displayName,
//                    onRoleSelected = {
//                        selectedRole = Role.STUDENT.displayName
//                    },
//                    modifier = Modifier.weight(1f)
//                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF0961F5)
                ),

            )
            Text(
                stringResource(id = R.string.agree),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black.copy(
                    alpha = 0.7f
                )
            )
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp
                ),
            colors = ButtonColors(
                containerColor = Color(0xFF0961F5),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFF0961F5),
                disabledContentColor = Color.White
            ),
            enabled = isFormValid,
            onClick = onSignUp
        ) {
            Text(
                stringResource(R.string.signup),
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
                stringResource(id = R.string.or),
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
                stringResource(id = R.string.already),
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
                onClick = {

                }
            ) {
                Text(
                    stringResource(id = R.string.signin),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
fun CustomTextField(
    @StringRes title : Int,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(title),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black.copy(
                alpha = 0.7f
            ),
        )
        TextField(
            value = value,
            singleLine = true,
            onValueChange = onValueChange,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF4F6F9),
                disabledTextColor = Color.Gray,
                focusedContainerColor = Color(0xFFF4F6F9),
                errorLabelColor = Color.Red.copy(
                    alpha = 0.2f
                ),
                unfocusedTextColor = Color.Gray,
                focusedTextColor = Color.Black.copy(
                    alpha = 0.8f
                ),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun RoleSelection(
    role: Role,
    isSelected : Boolean,
    onRoleSelected : () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) Color(0xFF0961F5) else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onRoleSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.White.copy(
                    alpha = 0.8f
                ),
                unselectedColor = Color.Black.copy(
                    alpha = 0.8f
                )
            ),
            modifier = Modifier
                .weight(0.8f)
                .padding(
                    vertical = 16.dp,
                    horizontal = 8.dp
                ),
            //        colors = colors,
            //        elevation = ButtonDefaults.elevatedButtonElevation(
            //            defaultElevation = elevation,
            //            pressedElevation = elevation
            //        ),
            //        shape = RoundedCornerShape(8.dp)
        )
        Icon(
            painter = painterResource(role.icon),
            contentDescription = role.displayName,
            tint = Color.Unspecified,
            modifier = Modifier
                .weight(1f)

        )
        Text(
            role.displayName,
            style = MaterialTheme.typography.titleSmall,
            color = if (isSelected) Color.White else Color.Black.copy(
                alpha = 0.5f
            ),
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp)
        )
    }
}

enum class Role(
    val displayName: String,
    @DrawableRes val icon: Int
) {
    TEACHER(
        displayName = "Giáo viên",
        icon = R.drawable.teacher_svgrepo_com
    ),
    STUDENT(
        displayName = "Học viên",
        icon = R.drawable.graduated_student_svgrepo_com
    )
}

@Composable
@Preview
private fun SignupPreview() {
    EduConnectTheme {
        SignupScreen(
            navigateToInformationScreen = {},
//            viewModel = viewModel(
//                factory = EduViewModelProvider.Factory
//            ),
        )
    }
}